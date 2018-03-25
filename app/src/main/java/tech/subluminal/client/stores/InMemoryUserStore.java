package tech.subluminal.client.stores;

import tech.subluminal.shared.records.User;

/**
 * Stores client-side information about the users in memory.
 */
public class InMemoryUserStore implements UserStore {

  private final Object currentUserLock = new Object();
  private User currentUser;

  /**
   * @return the current user.
   */
  @Override
  public User getCurrentUser() {
    synchronized (currentUserLock) {
      return new User(currentUser.getUsername(), currentUser.getId());
    }
  }

  /**
   * @param user to be set as current user.
   */
  @Override
  public void setCurrentUser(User user) {
    synchronized (currentUserLock) {
      currentUser = user;
    }
  }

  @Override
  public User getUserByUsername(String username) {
    //TODO implement this
    return null;
  }
}
