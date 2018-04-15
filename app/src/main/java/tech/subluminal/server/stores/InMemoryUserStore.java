package tech.subluminal.server.stores;

import tech.subluminal.shared.stores.IdentifiableCollection;
import tech.subluminal.shared.stores.records.User;

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
