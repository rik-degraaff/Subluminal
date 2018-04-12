package tech.subluminal.server.logic.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.stores.GameStore;
import tech.subluminal.server.stores.LobbyStore;
import tech.subluminal.shared.logic.game.GameLoop;
import tech.subluminal.shared.messages.FleetMoveReq;
import tech.subluminal.shared.messages.MotherShipMoveReq;
import tech.subluminal.shared.messages.MoveReq;
import tech.subluminal.shared.net.Connection;

public class GameManager {

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
    connection
        .registerHandler(FleetMoveReq.class, FleetMoveReq::fromSON, req -> onMoveRequest(req, id));
    connection.registerHandler(MotherShipMoveReq.class, MotherShipMoveReq::fromSON,
        req -> onMoveRequest(req, id));
  }

  private void onMoveRequest(MoveReq req, String id) {
    String gameID = ""; //TODO: get lobbyID from the lobby store and abort if the user is not one
    gameStore.moveRequests().getByID(gameID)
        .ifPresent(sync -> sync.consume(list -> list.add(id, req)));
  }

  public void startGame(String lobbyID, Set<String> playerIDs) {
    gameStore.games().add(MapGeneration.getNewGameStateForPlayers(playerIDs, lobbyID));

    GameLoop gameLoop = new GameLoop(TPS, new GameLoop.Delegate() {

      @Override
      public void beforeTick() {

      }

      @Override
      public void tick(long elapsedTime) {
        long currentTime = System.currentTimeMillis();
        gameStore.moveRequests().getByID(lobbyID)
            .map(sync -> sync.use(signals -> signals.));
      }

      @Override
      public void afterTick() {

      }
    });
    Thread gameThread = new Thread(gameLoop::start);
    gameThread.start();
    gameThreads.put(lobbyID, gameThread);
  }
}
