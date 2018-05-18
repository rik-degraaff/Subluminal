package tech.subluminal.server.logic.game;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.stores.GameStore;
import tech.subluminal.server.stores.HighScoreStore;
import tech.subluminal.server.stores.InMemoryGameStore;
import tech.subluminal.server.stores.InMemoryLobbyStore;
import tech.subluminal.server.stores.LobbyStore;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.Player;
import tech.subluminal.server.stores.records.Star;
import tech.subluminal.shared.logic.game.GameLoop.Delegate;
import tech.subluminal.shared.messages.YouLose;
import tech.subluminal.shared.records.LobbyStatus;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.LobbySettings;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.util.Synchronized;

public class GameManagerTest {

  private static final double SHIP_SPEED = 0.005;
  private static final double LIGHT_SPEED = 0.05;
  private static final double JUMP = 0.2;

  private GameStore gameStore;
  private LobbyStore lobbyStore;
  private HighScoreStore highScoreStore;
  private MessageDistributor distributor;

  @Before
  public void setup() {
    gameStore = new InMemoryGameStore();
    lobbyStore = new InMemoryLobbyStore();
    highScoreStore = new HighScoreStore();
    distributor = mock(MessageDistributor.class);
  }

  @Test
  public void motherShipKilledTest() {
    Map<String, Player> players = new HashMap<>();

    players.put(
        "0",
        new Player("0", "name0", Collections.singleton("1"),
            new Ship(new Coordinates(0, 0), "0", Collections.emptyList(), "0", SHIP_SPEED),
            LIGHT_SPEED)
    );

    players.put(
        "1",
        new Player("1", "name1", Collections.singleton("0"),
            new Ship(new Coordinates(0, 0), "1", Collections.emptyList(), "0", SHIP_SPEED),
            LIGHT_SPEED)
    );

    players.get("0").getFleets().put(
        "0",
        new GameHistory<>(
            players.keySet(),
            new GameHistoryEntry<>(
                new Fleet(new Coordinates(0, 0), 1, "0", Collections.emptyList(), "0", SHIP_SPEED))
            , LIGHT_SPEED
        )
    );

    final Set<Star> stars = Collections.singleton(
        new Star(null, 0, new Coordinates(0, 0), "0", false, JUMP, 1, 1, 1, 1, "")
    );

    runGame(distributor, players, stars, gameStore, delegate -> {
      delegate.tick(1.5);
      delegate.afterTick();
    });

    verify(distributor, atLeast(0))
        .sendMessage(not(isA(YouLose.class)), any(String.class));
    verify(distributor, never()).sendMessage(any(YouLose.class), eq("0"));
    verify(distributor).sendMessage(any(YouLose.class), eq("1"));
    verify(distributor, never()).sendMessage(any(YouLose.class), eq("0"));
  }

  @Test
  public void dematerializeTest() {
    Map<String, Player> players = new HashMap<>();

    final Coordinates starCoordinates = new Coordinates(0, 0);
    final Coordinates shipCoordinates = new Coordinates(1, 1);

    players.put(
        "0",
        new Player("0", "name0", Collections.singleton("1"),
            new Ship(shipCoordinates, "0", Collections.singletonList("0"), "0", SHIP_SPEED),
            LIGHT_SPEED)
    );

    players.put(
        "1",
        new Player("1", "name1", Collections.singleton("0"),
            new Ship(shipCoordinates, "1", Collections.singletonList("0"), "0", SHIP_SPEED),
            LIGHT_SPEED)
    );

    players.get("0").updateFleet(
        new Fleet(starCoordinates, 20, "0", Collections.emptyList(), "0", SHIP_SPEED)
    );

    players.get("1").updateFleet(
        new Fleet(starCoordinates, 7, "1", Collections.emptyList(), "0", SHIP_SPEED)
    );

    final Set<Star> stars = Collections.singleton(
        new Star(null, 0, starCoordinates, "0", false, JUMP, 1, 1, 1, 1, "")
    );

    runGame(distributor, players, stars, gameStore, delegate -> {
      delegate.tick(1.5);
      delegate.afterTick();
    });

    assertTrue("GameState was not written into GameStore.",
        gameStore.games().getByID("game").isPresent());

    final Synchronized<GameState> game = gameStore.games()
        .getByID("game")
        .get();

    final GameHistoryEntry<Fleet> fleet1 = game.use(state ->
        state.getPlayers().get("1").getFleets().get("1").getCurrent()
    );

    assertTrue("A fleet of 7 ships facing a fleet of 20+ was not destroyed", fleet1.isDestroyed());

    final GameHistoryEntry<Fleet> fleet0 = game.use(state ->
        state.getPlayers().get("0").getFleets().get("0").getCurrent()
    );

    assertFalse("A fleet of 20+ ships facing a fleet of 7 was destroyed", fleet0.isDestroyed());
    assertTrue("A fleet of 20+ ships facing a fleet of 7 didn't lose at least 6 ships",
        fleet0.getState().getNumberOfShips() <= 15);
  }

  private GameManager runGame(
      MessageDistributor distributor, Map<String, Player> players,
      Set<Star> stars, GameStore gameStore, Consumer<Delegate> gameLoop
  ) {
    BlockingQueue<Boolean> queue = new LinkedBlockingDeque<>();
    lobbyStore.lobbies().add(new Lobby("game", null, LobbyStatus.FULL));

    final GameManager gameManager = new GameManager(
        gameStore, lobbyStore, distributor, highScoreStore,
        (ps, id) -> new GameState(id, stars, new HashSet<>(players.values()), 0.05, JUMP,
            SHIP_SPEED),
        (tps, delegate) -> () -> {
          try {
            gameLoop.accept(delegate);
            queue.add(false);
          } catch (Exception e) {
            e.printStackTrace();
            queue.add(true);
          }
        }
    );

    Map<String, String> playerNames = new HashMap<>();
    players.forEach((id, player) -> {
      playerNames.put(id, player.getName());
    });

    gameManager.startGame("game", playerNames);

    try {
      if (queue.take()) {
        fail("The game loop threw an exception.");
      }
    } catch (InterruptedException e) {
      fail("The game loop didn't finish running.");
    }

    return gameManager;
  }
}
