package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.SingleEntity;
import tech.subluminal.shared.stores.records.User;

/**
 * Stores client-side information about the users in memory.
 */
public class InMemoryUserStore implements UserStore {

  private final SingleEntity<User> currentUser = new SingleEntity<>();
  private final UserCollection users = new UserCollection();

  @Override
  public SingleEntity<User> currentUser() {
    return currentUser;
  }

  @Override
  public UserCollection users() {
    return users;
  }
}
