package tech.subluminal.client.stores;

import tech.subluminal.shared.records.User;

public interface ReadOnlyUserStore {

  /**
   * @return the current user.
   */
  User getCurrentUser();
}
