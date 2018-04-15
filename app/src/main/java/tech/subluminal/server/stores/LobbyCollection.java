package tech.subluminal.server.stores;

import java.util.Collection;
import tech.subluminal.shared.stores.IdentifiableCollection;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.util.Synchronized;

public class LobbyCollection extends IdentifiableCollection<Lobby> {

  public Synchronized<Collection<Synchronized<Lobby>>> getLobbiesWithUser(String id) {
    return getWithPredicate(lobby -> lobby.getPlayers().contains(id));
  }
}
