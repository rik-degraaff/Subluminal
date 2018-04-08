package tech.subluminal.server.stores;

import java.util.Collection;
import tech.subluminal.shared.stores.records.SentPing;

/**
 * Saves server-side information about the pings of clients.
 */
public interface PingStore {

  /**
   * @return an IdentifiableCollection containing all sent pings that haven't been replied to yet.
   */
  PingCollection sentPings();
}
