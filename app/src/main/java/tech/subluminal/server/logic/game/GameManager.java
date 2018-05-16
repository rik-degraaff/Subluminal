package tech.subluminal.server.logic.game;

import static tech.subluminal.shared.util.ColorUtils.getNiceColors;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import org.pmw.tinylog.Logger;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.stores.GameStore;
import tech.subluminal.server.stores.HighScoreStore;
import tech.subluminal.server.stores.LobbyStore;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.HighScore;
import tech.subluminal.server.stores.records.MoveRequests;
import tech.subluminal.server.stores.records.Player;
import tech.subluminal.server.stores.records.Signal;
import tech.subluminal.server.stores.records.Star;
import tech.subluminal.shared.logic.game.GameLoop;
import tech.subluminal.shared.logic.game.SleepGameLoop;
import tech.subluminal.shared.messages.EndGameRes;
import tech.subluminal.shared.messages.FleetMoveReq;
import tech.subluminal.shared.messages.GameLeaveReq;
import tech.subluminal.shared.messages.GameLeaveRes;
import tech.subluminal.shared.messages.GameStartRes;
import tech.subluminal.shared.messages.GameStateDelta;
import tech.subluminal.shared.messages.HighScoreReq;
import tech.subluminal.shared.messages.HighScoreRes;
import tech.subluminal.shared.messages.LobbyUpdateRes;
import tech.subluminal.shared.messages.MotherShipMoveReq;
import tech.subluminal.shared.messages.MoveReq;
import tech.subluminal.shared.messages.YouLose;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.LobbyStatus;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.util.IdUtils;
import tech.subluminal.shared.util.Synchronized;
import tech.subluminal.shared.util.function.Either;

/**
 * contains the server side logic pertaining to games, including the actual game logic.
 */
public class GameManager implements GameStarter {

  private static final int TPS = 10;
  private final GameStore gameStore;
  private final LobbyStore lobbyStore;
  private final MessageDistributor distributor;
  private final Map<String, Thread> gameThreads = new HashMap<>();
  private final HighScoreStore highScoreStore;
  private final BiFunction<Map<String, String>, String, GameState> mapGenerator;
  private final BiFunction<Integer, SleepGameLoop.Delegate, GameLoop> gameLoopProvider;

  public GameManager(
      GameStore gameStore, LobbyStore lobbyStore, MessageDistributor distributor,
      HighScoreStore highScoreStore,
      BiFunction<Map<String, String>, String, GameState> mapGenerator,
      BiFunction<Integer, SleepGameLoop.Delegate, GameLoop> gameLoopProvider
  ) {
    this.gameStore = gameStore;
    this.lobbyStore = lobbyStore;
    this.highScoreStore = highScoreStore;
    this.distributor = distributor;

    this.mapGenerator = mapGenerator;
    this.gameLoopProvider = gameLoopProvider;

    distributor.addConnectionOpenedListener(this::attachHandlers);
    distributor.addConnectionOpenedListener((id, c) -> userConnected(id));
  }

  private void attachHandlers(String id, Connection connection) {
    connection.registerHandler(FleetMoveReq.class, FleetMoveReq::fromSON,
        req -> onMoveRequest(req, id));
    connection.registerHandler(MotherShipMoveReq.class, MotherShipMoveReq::fromSON,
        req -> onMoveRequest(req, id));
    connection.registerHandler(HighScoreReq.class, HighScoreReq::fromSON,
        req -> onHighScoreReq(id));
    connection.registerHandler(GameLeaveReq.class, GameLeaveReq::fromSON,
        req -> onLeaveGame(req, id));
  }

  private void onLeaveGame(GameLeaveReq req, String id) {
    getGameWithUser(id).ifPresent(sync -> sync.consume(state -> {
      final Player player = state.getPlayers().get(id);
      final GameHistory<Ship> motherShip = player.getMotherShip();
      motherShip.add(GameHistoryEntry.destroyed(motherShip.getCurrent().getState()));
      player.leave();
      lobbyStore.lobbies()
          .getByID(state.getID())
          .ifPresent(syncLobby -> {
            syncLobby.consume(lobby -> {
              lobby.removePlayer(id);
              distributor.sendMessage(new LobbyUpdateRes(lobby), lobby.getPlayers());
            });
          });
    }));
    distributor.sendMessage(new GameLeaveRes(), id);
  }

  private void userConnected(String id) {
    getGameWithUser(id).ifPresent(sync -> sync.consume(state -> {
      final Map<String, Player> players = state.getPlayers();
      final Player player = players.get(id);
      final GameHistoryEntry<Ship> motherShipEntry = player.getMotherShip().getCurrent();
      if (player.isAlive() && !motherShipEntry.isDestroyed()) {
        List<Color> colors = getNiceColors(players.size());
        int i = 0;
        Map<String, Color> playerColors = new HashMap<>();
        for (String p : players.values().stream().map(Player::getID).collect(Collectors.toList())) {
          playerColors.put(p, colors.get(i));
          i++;
        }
        distributor.sendMessage(new GameStartRes(state.getID(), playerColors), id);
        player.join();
        GameStateDelta delta = new GameStateDelta();
        delta.addPlayer(
            createInitialPlayerDelta(Optional.of(motherShipEntry.getState()), player, id));
        players.keySet()
            .stream()
            .filter(playerID -> !playerID.equals(id))
            .map(players::get)
            .map(otherPlayer -> createInitialPlayerDelta(
                otherPlayer.getMotherShip().getLastForPlayer(id).left(), otherPlayer, id
            ))
            .forEach(delta::addPlayer);

        state.getStars()
            .values()
            .stream()
            .map(history -> history.getLastForPlayer(id).left())
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(delta::addStar);

        distributor.sendMessage(delta, id);
      }
    }));
  }

  private Optional<Synchronized<GameState>> getGameWithUser(String id) {
    return lobbyStore.lobbies()
        .getLobbiesWithUser(id)
        .use(l -> l.stream().map(s -> s.use(Lobby::getID)))
        .findFirst()
        .flatMap(gameID -> gameStore.games().getByID(gameID));
  }

  private void onHighScoreReq(String id) {
    distributor
        .sendMessage(new HighScoreRes(highScoreStore.highScores().use(Function.identity())), id);
  }

  private void onMoveRequest(MoveReq req, String id) {
    Optional<String> optGameID = lobbyStore.lobbies()
        .getLobbiesWithUser(id)
        .use(l -> l.stream().map(s -> s.use(Lobby::getID)))
        .findFirst();
    Logger.trace("MOVE REQUESTS: " + gameStore.moveRequests().getByID(optGameID.get()));
    optGameID.ifPresent(gameID -> {
      gameStore.moveRequests().getByID(gameID)
          .ifPresent(sync -> sync.consume(list -> list.add(id, req)));
    });
  }

  /**
   * Starts a new game.
   *
   * @param lobbyID the id of the lobby this game belongs to.
   * @param players the players participating in this game.
   */
  @Override
  public void startGame(String lobbyID, Map<String, String> players) {
    gameStore.games().add(mapGenerator.apply(players, lobbyID));
    gameStore.moveRequests().add(new MoveRequests(lobbyID));

    GameLoop gameLoop = gameLoopProvider.apply(TPS, new SleepGameLoop.Delegate() {

      @Override
      public void beforeTick() {
        gameStore.moveRequests()
            .getByID(lobbyID)
            .ifPresent(syncReqs -> processMoveRequests(syncReqs, lobbyID, players.keySet()));
      }

      @Override
      public void tick(double elapsedTime) {
        gameStore.games()
            .getByID(lobbyID)
            .ifPresent(sync -> sync.consume(gameState -> gameTick(gameState, elapsedTime)));
      }

      @Override
      public boolean afterTick() {
        AtomicBoolean stop = new AtomicBoolean(false);

        gameStore.games()
            .getByID(lobbyID)
            .ifPresent(sync -> sync.consume(gameState -> {
              if (sendUpdatesToPlayers(gameState)) {
                stop.set(true);
                gameThreads.remove(lobbyID);
                lobbyStore.lobbies()
                    .getByID(lobbyID)
                    .ifPresent(syncLobby -> {
                      syncLobby.consume(lobby -> lobby.setStatus(LobbyStatus.LOCKED));
                    });
              }
            }));
        return stop.get();
      }
    });
    Thread gameThread = new Thread(gameLoop::start);
    gameThread.start();
    gameThreads.put(lobbyID, gameThread);
  }

  private boolean sendUpdatesToPlayers(GameState gameState) {
    AtomicBoolean playersDestroyed = new AtomicBoolean(false);
    gameState.getPlayers().keySet().forEach(playerID -> {
      final GameStateDelta delta = new GameStateDelta();

      final Player currentPlayer = gameState.getPlayers()
          .get(playerID);

      final GameHistoryEntry<Ship> motherShipEntry = currentPlayer.getMotherShip().getCurrent();

      if (motherShipEntry.isDestroyed()) {
        if (currentPlayer.isAlive()) {
          delta.addRemovedMotherShip(motherShipEntry.getState().getID());
          distributor.sendMessage(new YouLose(), playerID);
          currentPlayer.kill();
          playersDestroyed.set(true);
        }
      } else {
        final Optional<Ship> ship = motherShipEntry.isDestroyed()
            ? Optional.empty()
            : Optional.ofNullable(motherShipEntry.getState());
        delta.addPlayer(createPlayerDelta(ship, motherShipEntry,
            gameState.getPlayers().get(playerID), delta, playerID));
      }

      gameState.getPlayers().forEach((deltaPlayerID, player) -> {
        if (!deltaPlayerID.equals(playerID)) {
          final Optional<Ship> motherShip = player.getMotherShip()
              .getLatestOrLastForPlayer(playerID, motherShipEntry)
              .left();

          delta.addPlayer(createPlayerDelta(motherShip, motherShipEntry,
              gameState.getPlayers().get(deltaPlayerID), delta, playerID));

          if (!motherShip.isPresent()) {
            delta.addRemovedMotherShip(player.getMotherShip().getCurrent().getState().getID());
          }
        }
      });

      gameState.getStars().forEach((starID, starHistory) ->
          starHistory.getLatestForPlayer(playerID, motherShipEntry)
              .flatMap(Either::left)
              .ifPresent(delta::addStar));

      if (!gameState.getPlayers().get(playerID).hasLeft()) {
        distributor.sendMessage(delta, playerID);
      }
    });

    if (playersDestroyed.get()) {
      final List<Player> livingPlayers = gameState.getPlayers().values().stream()
          .filter(Player::isAlive)
          .collect(Collectors.toList());

      if (livingPlayers.size() <= 1) {
        String winner = livingPlayers.size() == 1 ? livingPlayers.get(0).getID() : null;
        distributor.sendMessage(new EndGameRes(gameState.getID(), winner),
            gameState.getPlayers().keySet());
        gameThreads.remove(gameState.getID());
        if (winner != null) {
          final Player winnerPlayer = gameState.getPlayers().get(winner);
          System.out.println("enemyShips: " + winnerPlayer.getEnemyShipsDematerialized());
          System.out.println("ownShips: " + winnerPlayer.getDematerializedShips());
          final double diff =
              winnerPlayer.getEnemyShipsDematerialized() - winnerPlayer.getDematerializedShips();
          final double total = gameState.getPlayers()
              .values()
              .stream()
              .mapToDouble(Player::getDematerializedShips)
              .sum();
          final double score = (1000.0 * diff) / total;
          highScoreStore.highScores()
              .update(old -> {
                old.add(new HighScore(winnerPlayer.getName(), score));
                old.sort(Comparator.comparingDouble(HighScore::getScore));
                return old.subList(0, Math.min(9, old.size()));
              });
        }
        return true;
      }
    }
    return false;
  }

  private tech.subluminal.client.stores.records.game.Player createInitialPlayerDelta(
      Optional<Ship> motherShip, Player player, String forPlayerID
  ) {
    tech.subluminal.client.stores.records.game.Player playerDelta =
        new tech.subluminal.client.stores.records.game.Player(player.getID(), motherShip,
            new LinkedList<>());

    player.getFleets()
        .values()
        .stream()
        .map(history -> history.getLastForPlayer(forPlayerID).left())
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(playerDelta.getFleets()::add);

    return playerDelta;
  }

  private tech.subluminal.client.stores.records.game.Player createPlayerDelta(
      Optional<Ship> motherShip,
      GameHistoryEntry<Ship> motherShipEntry, Player player, GameStateDelta delta,
      String forPlayerID
  ) {
    tech.subluminal.client.stores.records.game.Player playerDelta =
        new tech.subluminal.client.stores.records.game.Player(player.getID(), motherShip,
            new LinkedList<>());

    Set<String> removedHistories = new HashSet<>();

    // loop through all fleets of the player
    player.getFleets().forEach((fleetID, fleetHistory) -> {
      fleetHistory.getLatestForPlayer(forPlayerID, motherShipEntry)
          .ifPresent(fleetState -> {
            fleetState.apply(
                // if a new state for the fleet is available for the player, write it in the playerDelta.
                playerDelta.getFleets()::add,
                // if the fleet was destroyed, add it to the removed fleet list and remove the history if possible
                v -> {
                  if (fleetHistory.canBeRemoved()) {
                    removedHistories.add(fleetID);
                  }
                  delta.addRemovedFleet(player.getID(), fleetID);
                }
            );
          });
    });

    removedHistories.forEach(player.getFleets()::remove);

    return playerDelta;
  }

  private void gameTick(GameState gameState, double elapsedTime) {
    final Map<String, Star> stars = gameState.getStars()
        .entrySet()
        .stream()
        .filter(e -> !e.getValue().getCurrent().isDestroyed())
        .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().getCurrent().getState()));

    final IntermediateGameState intermediateGameState =
        new IntermediateGameState(elapsedTime, stars, gameState.getPlayers().keySet(),
            gameState.getShipSpeed(), gameState.getSignals());

    gameState.getPlayers().forEach((playerID, player) -> {
      player.getFleets()
          .values()
          .stream()
          .map(GameHistory::getCurrent)
          .filter(s -> !s.isDestroyed())
          .map(GameHistoryEntry::getState)
          .forEach(fleet -> intermediateGameState.addFleet(fleet, playerID));

      if (!player.getMotherShip().getCurrent().isDestroyed()) {
        intermediateGameState
            .addMotherShip(player.getMotherShip().getCurrent().getState(), playerID);
      }
    });

    intermediateGameState.advance();

    intermediateGameState.getDematerializedEnemyShips().forEach((playerID, amount) -> {
      gameState.getPlayers().get(playerID).addEnemyShipsDematerialized(amount);
    });

    intermediateGameState.getDematerializedShips().forEach((playerID, amount) -> {
      gameState.getPlayers().get(playerID).addDematerializedShips(amount);
    });

    intermediateGameState.getStars().forEach((starID, star) -> {
      gameState.getStars().get(starID).add(new GameHistoryEntry<>(star));
    });

    intermediateGameState.getFleetsOnStars().forEach((starID, map) -> {
      map.forEach((playerID, optFleet) -> {
        final Player player = gameState.getPlayers().get(playerID);
        optFleet.ifPresent(player::updateFleet);
      });
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

    intermediateGameState.getDestroyedFleets().forEach((playerID, fleets) -> {
      final Map<String, GameHistory<Fleet>> fleetHistories = gameState.getPlayers().get(playerID)
          .getFleets();
      fleets.forEach(f -> {
        final GameHistory<Fleet> history = fleetHistories.get(f.getID());
        if (history != null) {
          history.add(GameHistoryEntry.destroyed(f));
        }
      });
    });

    intermediateGameState.getDestroyedPlayers().forEach(player -> {
      final GameHistory<Ship> history = gameState.getPlayers().get(player).getMotherShip();
      history.add(GameHistoryEntry.destroyed(history.getCurrent().getState()));
    });

    gameState.setSignals(intermediateGameState.getSignals());
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
      requestMap.forEach((playerID, requests) -> {
        if (!gameState.getPlayers().get(playerID).getMotherShip().getCurrent().isDestroyed()) {
          requests.forEach(req -> handleRequest(gameState, playerID, req));
        }
      });
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
