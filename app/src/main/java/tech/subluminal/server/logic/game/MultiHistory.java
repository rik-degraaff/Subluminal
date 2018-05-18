package tech.subluminal.server.logic.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Allows the storage of states which can be accessed by multiple clients in FIFO order.
 */
public class MultiHistory<K, V> {

  protected final Map<K, Queue<V>> history = new HashMap<>();
  private final Set<K> keys;
  private V lastUpdated;
  private V current;

  /**
   * @param keys a set of keys identifying clients that can later be used to access the history.
   * @param initial the initial state.
   */
  public MultiHistory(Set<K> keys, V initial) {
    this.keys = keys;
    keys.forEach(key -> {
      Queue<V> q = new LinkedList<>();
      history.put(key, q);
    });
    add(initial);
  }

  /**
   * @param entry the entry that will be added to the history.
   */
  public void add(V entry) {
    if (!entry.equals(lastUpdated)) {
      lastUpdated = entry;
      keys.forEach(key -> history.get(key).offer(entry));
    }
    current = entry;
  }

  /**
   * @return the current state.
   */
  public V getCurrent() {
    return current;
  }

  /**
   * @param key the key identifying the client polling for the latest state.
   * @param predicate a predicate which should return true if the client can access a new state.
   * @return the latest unread state for this client.
   */
  public Optional<V> getLatestForKeyIf(K key, Predicate<V> predicate) {
    return Optional.ofNullable(history.get(key))
        .filter(q -> !q.isEmpty())
        .filter(q -> predicate.test(q.peek()))
        .map(q -> {
          V state;
          do {
            state = q.poll();
          } while (!q.isEmpty() && predicate.test(q.peek()));
          return state;
        });
  }
}
