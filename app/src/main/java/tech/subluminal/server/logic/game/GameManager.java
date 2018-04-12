package tech.subluminal.server.logic.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.stores.GameStore;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.shared.logic.game.GameLoop;

public class GameManager {

  private final GameStore gameStore;
  private final MessageDistributor distributor;
  private final Map<String, Thread> gameThreads = new HashMap<>();

  public GameManager(GameStore gameStore, MessageDistributor distributor) {
    this.gameStore = gameStore;
    this.distributor = distributor;
  }

  public void startGame(String lobbyID, Set<String> playerIDs) {
    gameStore.games().add(MapGeneration.getNewGameStateForPlayers(playerIDs, lobbyID));

    GameLoop gameLoop = new GameLoop(5, new GameLoop.Delegate() {

      @Override
      public void beforeTick() {

      }

      @Override
      public void tick(long elapsedTime) {

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
