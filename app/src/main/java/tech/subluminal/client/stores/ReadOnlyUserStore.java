package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.records.User;

public interface ReadOnlyUserStore {

  /**
   * Get the current user.
   *
   * @return the current user.
   */
  User getCurrentUser();

  /**
   * Check with a username if a specific user exists.
   *
   * @return the found user object.
   */
  User getUserByUsername(String username);
}
