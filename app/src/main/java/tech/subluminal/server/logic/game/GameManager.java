package tech.subluminal.server.logic.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.pmw.tinylog.Logger;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.stores.GameStore;
import tech.subluminal.server.stores.LobbyStore;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.MoveRequests;
import tech.subluminal.server.stores.records.Player;
import tech.subluminal.server.stores.records.Signal;
import tech.subluminal.server.stores.records.Star;
import tech.subluminal.shared.logic.game.GameLoop;
import tech.subluminal.shared.messages.FleetMoveReq;
import tech.subluminal.shared.messages.GameStateDelta;
import tech.subluminal.shared.messages.MotherShipMoveReq;
import tech.subluminal.shared.messages.MoveReq;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.util.IdUtils;
import tech.subluminal.shared.util.Synchronized;
import tech.subluminal.shared.util.function.Either;

public class GameManager implements GameStarter {

  private static final int TPS = 5;
  private final GameStore gameStore;
  private final LobbyStore lobbyStore;
  private final MessageDistributor distributor;
  private final Map<String, Thread> gameThreads = new HashMap<>();

  public GameManager(GameStore gameStore, LobbyStore lobbyStore, MessageDistributor distributor) {
    this.gameStore = gameStore;
    this.lobbyStore = lobbyStore;
    this.distributor = distributor;

    distributor.addConnectionOpenedListener(this::attachHandlers);
  }

  private void attachHandlers(String id, Connection connection) {
    connection.registerHandler(FleetMoveReq.class, FleetMoveReq::fromSON,
        req -> onMoveRequest(req, id));
    connection.registerHandler(MotherShipMoveReq.class, MotherShipMoveReq::fromSON,
        req -> onMoveRequest(req, id));
  }

  private void onMoveRequest(MoveReq req, String id) {
    Optional<String> optGameID = lobbyStore.lobbies()
        .getLobbiesWithUser(id)
        .use(l -> l.stream().map(s -> s.use(Lobby::getID)))
        .findFirst();
    Logger.debug("MOVE REQUESTS: " + gameStore.moveRequests().getByID(optGameID.get()));
    optGameID.ifPresent(gameID -> {
      gameStore.moveRequests().getByID(gameID)
          .ifPresent(sync -> sync.consume(list -> list.add(id, req)));
    });
  }

  @Override
  public void startGame(String lobbyID, Set<String> playerIDs) {
    gameStore.games().add(MapGeneration.getNewGameStateForPlayers(playerIDs, lobbyID));
    gameStore.moveRequests().add(new MoveRequests(lobbyID));

    GameLoop gameLoop = new GameLoop(TPS, new GameLoop.Delegate() {

      @Override
      public void beforeTick() {
        gameStore.moveRequests()
            .getByID(lobbyID)
            .ifPresent(syncReqs -> processMoveRequests(syncReqs, lobbyID, playerIDs));
      }

      @Override
      public void tick(long elapsedTime) {
        gameStore.games()
            .getByID(lobbyID)
            .ifPresent(sync -> sync.consume(gameState -> gameTick(gameState, elapsedTime)));
      }

      @Override
      public void afterTick() {
        gameStore.games()
            .getByID(lobbyID)
            .ifPresent(sync -> sync.consume(gameState -> sendUpdatesToPlayers(gameState)));
      }
    });
    Thread gameThread = new Thread(gameLoop::start);
    gameThread.start();
    gameThreads.put(lobbyID, gameThread);
  }

  private void sendUpdatesToPlayers(GameState gameState) {
    gameState.getPlayers().keySet().forEach(playerID -> {
      final GameStateDelta delta = new GameStateDelta();

      final GameHistoryEntry<Ship> motherShipEntry = gameState.getPlayers()
          .get(playerID)
          .getMotherShip()
          .getCurrent();
      if (motherShipEntry.isDestroyed()) {
        delta.addRemovedPlayer(playerID);
        //TODO: inform the player that they lost?
      } else {
        delta.addPlayer(createPlayerDelta(motherShipEntry.getState(), motherShipEntry,
            gameState.getPlayers().get(playerID), delta, playerID));
      }

      gameState.getPlayers()
          .keySet()
          .stream()
          .filter(id -> !id.equals(playerID))
          .forEach(deltaPlayerID -> {
            gameState.getPlayers()
                .get(deltaPlayerID)
                .getMotherShip()
                .getLatestOrLastForPlayer(playerID, motherShipEntry)
                .apply(
                    motherShip -> delta.addPlayer(createPlayerDelta(motherShip, motherShipEntry,
                        gameState.getPlayers().get(deltaPlayerID), delta, playerID)),
                    v -> delta.addRemovedPlayer(deltaPlayerID)
                );
          });

      gameState.getStars().forEach((starID, starHistory) ->
          starHistory.getLatestForPlayer(playerID, motherShipEntry)
              .flatMap(Either::left)
              .ifPresent(delta::addStar));

      distributor.sendMessage(delta, playerID);
    });
  }

  private tech.subluminal.client.stores.records.game.Player createPlayerDelta(
      Ship motherShip,
      GameHistoryEntry<Ship> motherShipEntry, Player player, GameStateDelta delta,
      String forPlayerID
  ) {
    tech.subluminal.client.stores.records.game.Player playerDelta =
        new tech.subluminal.client.stores.records.game.Player(player.getID(), motherShip,
            new LinkedList<>());

    // loop through all fleets of the player
    player.getFleets().forEach((fleetID, fleetHistory) -> {
      fleetHistory.getLatestForPlayer(forPlayerID, motherShipEntry)
          .ifPresent(fleetState -> {
            fleetState.apply(
                // if a new state for the fleet is available for the player, write it in the playerDelta.
                playerDelta::updateFleet,
                // if the fleet was destroyed, add it to the removed fleet list and remove the history if possible
                v -> {
                  if (fleetHistory.canBeRemoved()) {
                    player.getFleets().remove(fleetID);
                  }
                  delta.addRemovedFleet(player.getID(), fleetID);
                }
            );
          });
    });

    return playerDelta;
  }

  private void gameTick(GameState gameState, long elapsedTime) {
    final Map<String, Star> stars = gameState.getStars()
        .entrySet()
        .stream()
        .filter(e -> !e.getValue().getCurrent().isDestroyed())
        .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().getCurrent().getState()));

    final IntermediateGameState intermediateGameState =
        new IntermediateGameState(elapsedTime, stars, gameState.getPlayers().keySet());

    gameState.getPlayers().forEach((playerID, player) -> {
      player.getFleets()
          .values()
          .stream()
          .map(GameHistory::getCurrent)
          .filter(s -> !s.isDestroyed())
          .map(GameHistoryEntry::getState)
          .forEach(fleet -> {
            intermediateGameState.addFleet(fleet, playerID);
          });

      intermediateGameState.addMotherShip(player.getMotherShip().getCurrent().getState(), playerID);
    });

    intermediateGameState.advance();

    intermediateGameState.getStars().forEach((starID, star) -> {
      gameState.getStars().get(starID).add(new GameHistoryEntry<>(star));
    });

    intermediateGameState.getFleetsOnStars().forEach((playerID, map) -> {
      final Player player = gameState.getPlayers().get(playerID);
      map.values()
          .stream()
          .filter(Optional::isPresent)
          .map(Optional::get)
          .forEach(
              fleet -> player.getFleets().get(fleet.getID()).add(new GameHistoryEntry<>(fleet)));
    });

    intermediateGameState.getFleetsUnderway().forEach((playerID, fleets) -> {
      final Player player = gameState.getPlayers().get(playerID);
      fleets.forEach((fleetID, fleet) -> player.updateFleet(fleet));
    });

    intermediateGameState.getMotherShipsOnStars().forEach((starID, map) -> {
      map.forEach((playerID, optShip) -> {
        final Player player = gameState.getPlayers().get(playerID);
        optShip.ifPresent(
            ship -> player.getMotherShip().add(new GameHistoryEntry<>(ship)));
      });
    });

    intermediateGameState.getMotherShipsUnderway().forEach((playerID, optionalShip) -> {
      optionalShip.ifPresent(ship -> {
        final Player player = gameState.getPlayers().get(playerID);
        player.getMotherShip().add(new GameHistoryEntry<>(ship));
      });
    });
  }

  private void processMoveRequests(
      Synchronized<MoveRequests> syncReqs, String lobbyID, Set<String> playerIDs
  ) {
    Map<String, List<MoveReq>> requestMap = syncReqs.use(reqs -> {
      Map<String, List<MoveReq>> map = new HashMap<>();
      playerIDs.forEach(id -> map.put(id, reqs.getAndRemoveForPlayer(id)));
      return map;
    });

    // get the game state from the store and loop over the move requests, handling each one.
    gameStore.games().getByID(lobbyID).ifPresent(sync -> sync.consume(gameState -> {
      requestMap.forEach((playerID, requests) ->
          requests.forEach(req -> handleRequest(gameState, playerID, req)));
    }));
  }

  private void handleRequest(GameState gameState, String playerID, MoveReq moveReq) {
    final Player player = gameState.getPlayers().get(playerID);
    final Ship motherShip = player.getMotherShip().getCurrent().getState();
    if (moveReq instanceof MotherShipMoveReq) {
      MotherShipMoveReq req = (MotherShipMoveReq) moveReq;

      if (!isValidMove(gameState, motherShip.getCoordinates(), moveReq.getTargets())) {
        return;
      }

      final GameHistoryEntry<Ship> entry = new GameHistoryEntry<>(
          new Ship(motherShip.getCoordinates(), motherShip.getID(), req.getTargets(),
              req.getTargets().get(req.getTargets().size() - 1), motherShip.getSpeed()));

      // write the updated mother ship directly into the game store.
      player.getMotherShip().add(entry);
    } else if (moveReq instanceof FleetMoveReq) {
      FleetMoveReq req = (FleetMoveReq) moveReq;

      if (!isValidMove(gameState, req.getOriginID(), moveReq.getTargets())) {
        return;
      }

      // create a signal for the move request and write it into the game store
      gameState.getSignals().add(new Signal(motherShip.getCoordinates(),
          IdUtils.generateId(8), req.getOriginID(), req.getTargets(), playerID,
          gameState.getStars().get(req.getOriginID()).getCurrent().getState().getCoordinates(),
          req.getAmount(), gameState.getLightSpeed()));
    }
  }

  private boolean isValidMove(GameState gameState, String origin, List<String> targets) {
    GameHistory<Star> starHistory = gameState.getStars().get(origin);
    return isValidMove(gameState, starHistory.getCurrent().getState().getCoordinates(), targets);
  }

  private boolean isValidMove(GameState gameState, Coordinates origin, List<String> targets) {
    Logger.debug("IS VALID MOVE!!!!!???????");
    Coordinates lastPos = origin;
    for (String target : targets) {
      GameHistory<Star> starHistory = gameState.getStars().get(target);
      if (starHistory == null) {
        Logger.debug("STARHISTORY IS NULL");
        return false;
      }

      Star star = starHistory.getCurrent().getState();
      if (gameState.getJump() < star.getCoordinates().getDistanceFrom(lastPos)) {
        Logger.debug("JUMP TOO FAR");
        return false;
      }

      lastPos = star.getCoordinates();
    }
    return true;
  }
}
