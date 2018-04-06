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

  final private PingCollection pings = new PingCollection();

  /**
   * @return an IdentifiableCollection containing all sent pings that haven't been replied to yet.
   */
  @Override
  public PingCollection sentPings() {
    return pings;
  }
}
