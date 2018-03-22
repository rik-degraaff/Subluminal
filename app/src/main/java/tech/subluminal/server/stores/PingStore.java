package tech.subluminal.server.stores;

import java.util.Set;
import tech.subluminal.shared.records.SentPing;

/**
 * Saves server-side information about the pings of clients.
 */
public interface PingStore {

  /**
   * Adds a ping record to te store for a given user.
   *
   * @param userId the user the ping was sent to.
   * @param ping the ping that was sent.
   */
  void addPing(String userId, SentPing ping);

  /**
   * Removes a ping record from the store.
   *
   * @param userId the id of the user the ping to be removed was sent to.
   * @param pingId the id of the ping to be removed.
   */
  void removePing(String userId, String pingId);

  /**
   * Returns all the open pings that were sent to a user.
   *
   * @param userId the id of the user whose pings should be retrieved.
   * @return all the pings that were sent to this client.
   */
  Set<SentPing> getPings(String userId);
}
