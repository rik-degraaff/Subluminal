package tech.subluminal.server.logic.game;

import static junit.framework.TestCase.fail;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.Test;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.stores.GameStore;
import tech.subluminal.server.stores.InMemoryGameStore;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.Player;
import tech.subluminal.server.stores.records.Star;
import tech.subluminal.shared.messages.YouLose;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;

public class GameManagerTest {

  @Test
  public void motherShipKilledTest() {
    final GameStore gameStore = new InMemoryGameStore();
    MessageDistributor distributor = mock(MessageDistributor.class);

    Set<String> players = new HashSet<>(Arrays.asList("0", "1"));

    final Player player0 = new Player("0", Collections.singleton("1"),
        new Ship(new Coordinates(0, 0), "0", Collections.emptyList(), "0", 0.005),
        0.05);

    player0.getFleets().put(
        "0",
        new GameHistory<>(
            players,
            new GameHistoryEntry<>(
                new Fleet(new Coordinates(0, 0), 1, "0", Collections.emptyList(), "0", 0.005))
            , 0.05
        )
    );

    final Player player1 = new Player("1", Collections.singleton("0"),
        new Ship(new Coordinates(0, 0), "1", Collections.emptyList(), "0", 0.005),
        0.05);

    BlockingQueue<Object> queue = new LinkedBlockingDeque();

    final GameManager gameManager = new GameManager(
        gameStore, null, distributor, null,
        (ps, id) -> new GameState(id,
            Collections.singleton(
                new Star(null, 0, new Coordinates(0, 0), "0", false, 0.2, 1, 1, 1, 1, "")),
            new HashSet<>(Arrays.asList(player0, player1)),
            0.05, 0.2, 0.005),
        (tps, delegate) -> () -> {
          delegate.tick(1.5);
          delegate.tick(0.001);
          delegate.afterTick();
          queue.add(new Object());
        }
    );

    gameManager.startGame("lobbyID", players);

    try {
      queue.take();
    } catch (InterruptedException e) {
      fail("The game loop didn't finish running.");
    }

    verify(distributor, atLeast(0))
        .sendMessage(not(isA(YouLose.class)), any(String.class));
    verify(distributor, never()).sendMessage(any(YouLose.class), eq("0"));
    verify(distributor).sendMessage(any(YouLose.class), eq("1"));
    verify(distributor, never()).sendMessage(any(YouLose.class), eq("0"));
  }
}
