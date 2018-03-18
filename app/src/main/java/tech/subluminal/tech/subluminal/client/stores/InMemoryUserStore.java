package tech.subluminal.tech.subluminal.client.stores;

import tech.subluminal.shared.records.User;

/**
 * Stores client-side information about the users in memory.
 */
public class InMemoryUserStore implements UserStore{
  private User currentUser;

  /**
   * @return the current user.
   */
  @Override
  public User getCurrentUser() {
    return currentUser;
  }

  /**
   * @param user to be set as current user.
   */
  @Override
  public void setCurrentUser(User user) {
    currentUser = user;
  }
}
