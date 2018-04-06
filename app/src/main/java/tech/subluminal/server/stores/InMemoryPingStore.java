package tech.subluminal.server.stores;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import tech.subluminal.shared.stores.records.SentPing;

/**
 * Saves server-side information about the pings of clients in memory.
 */
public class InMemoryPingStore implements PingStore {

  private Map<String, Map<String, SentPing>> pings = new HashMap<>();

  /**
   * Adds a ping record to te store for a given user.
   *
   * @param userId the user the ping was sent to.
   * @param ping the ping that was sent.
   */
  @Override
  public void addPing(String userId, SentPing ping) {
    Map<String, SentPing> userPings = pings.get(userId);
    if (userPings == null) {
      userPings = new HashMap<>();
      pings.put(userId, userPings);
    }
    userPings.put(ping.getId(), ping);
  }

  /**
   * Removes a ping record from the store.
   *
   * @param userId the id of the user the ping to be removed was sent to.
   * @param pingId the id of the ping to be removed.
   */
  @Override
  public void removePing(String userId, String pingId) {
    Map<String, SentPing> userPings = pings.get(userId);
    if (userPings != null) {
      userPings.remove(pingId);
    }
  }

  /**
   * Returns all the open pings that were sent to a user.
   *
   * @param userId the id of the user whose pings should be retrieved.
   * @return all the pings that were sent to this client.
   */
  @Override
  public Collection<SentPing> getPings(String userId) {
    Map<String, SentPing> userPings = pings.get(userId);
    if (userPings == null) {
      return new HashSet<>();
    }
    return userPings.values();
  }

  /**
   * Returns the id's of users that have open pings they haven't responded to yet.
   *
   * @return the id's of users that have open pings they haven't responded to yet.
   */
  @Override
  public Collection<String> getUsersWithPings() {
    return pings.entrySet().stream()
        .filter(e -> !e.getValue().isEmpty())
        .map(e -> e.getKey())
        .collect(Collectors.toCollection(HashSet::new));
  }
}
