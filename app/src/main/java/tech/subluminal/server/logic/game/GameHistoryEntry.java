package tech.subluminal.server.logic.game;

import tech.subluminal.shared.stores.records.game.GameObject;

/**
 * An object which can be saved in a @link{GameHistory}.
 */
public class GameHistoryEntry<E extends GameObject> {

  private final E state;
  private final long time;

  public GameHistoryEntry(E state) {
    this(state, System.currentTimeMillis());
  }

  private GameHistoryEntry(E state, long time) {
    this.state = state;
    this.time = time;
  }

  /**
   * Creates an entry for the game history that has always been the same and can therefor be seen by all players
   *
   * @param state the state of the object.
   * @return an entry that can be used in a @link{GameHistory}.
   */
  public static <E extends GameObject> GameHistoryEntry<E> foreverAgo(E state) {
    return new GameHistoryEntry<>(state, Long.MIN_VALUE);
  }

  /**
   * @return the GameObject store in this entry.
   */
  public E getState() {
    return state;
  }

  /**
   * @return the time when this entry was created.
   */
  public long getTime() {
    return time;
  }
}
