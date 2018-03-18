package tech.subluminal.tech.subluminal.client.stores;

import tech.subluminal.shared.records.User;

/**
 * Stores client-side information about the users.
 */
public interface UserStore {

  /**
   * @return the current user.
   */
  User getCurrentUser();

  /**
   * @param user to be set as current user.
   */
  void setCurrentUser(User user);
}
