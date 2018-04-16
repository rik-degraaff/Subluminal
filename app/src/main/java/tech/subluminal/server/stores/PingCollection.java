package tech.subluminal.server.stores;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import tech.subluminal.shared.stores.IdentifiableCollection;
import tech.subluminal.shared.stores.records.SentPing;

public class PingCollection extends IdentifiableCollection<SentPing> {

  /**
   * Returns the id's of users with open pings they have not responded to yet.
   *
   * @return the id's of users that have open pings that they haven't responded to yet.
   */
  public Set<String> getUsersWithPings() {
    return getAll().use(pings ->
        pings.stream()
            .map(syncPing -> syncPing.use(SentPing::getUserID))
            .collect(Collectors.toSet())
    );
  }

  public void removeAll() {
    syncMap.consume(Map::clear);
  }
}
