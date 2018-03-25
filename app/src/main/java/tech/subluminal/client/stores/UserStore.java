package tech.subluminal.client.stores;

import tech.subluminal.shared.records.User;

/**
 * Stores client-side information about the users.
 */
public interface UserStore extends ReadOnlyUserStore {

  /**
   * Set the current user.
   *
   * @param user to be set as current user.
   */
  void setCurrentUser(User user);

}
