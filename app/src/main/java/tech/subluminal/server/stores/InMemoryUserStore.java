package tech.subluminal.server.stores;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import tech.subluminal.shared.stores.IdentifiableCollection;
import tech.subluminal.shared.stores.records.User;
import tech.subluminal.shared.util.Synchronized;

/**
 * Stores server-side information about the users in memory.
 */
public class InMemoryUserStore implements UserStore {

  private IdentifiableCollection<User> connectedUsers = new IdentifiableCollection<>();

  @Override
  public IdentifiableCollection<User> connectedUsers() {
    return connectedUsers;
  }
}
