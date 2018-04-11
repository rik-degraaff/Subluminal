package tech.subluminal.server.stores;

/**
 * Saves server-side information about the pings of clients.
 */
public interface PingStore {

  /**
   * @return an IdentifiableCollection containing all sent pings that haven't been replied to yet.
   */
  PingCollection sentPings();
}
