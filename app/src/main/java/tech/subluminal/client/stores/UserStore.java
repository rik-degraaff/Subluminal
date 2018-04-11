package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.SingleEntity;
import tech.subluminal.shared.stores.records.User;

/**
 * Stores client-side information about the users.
 */
public interface UserStore extends ReadOnlyUserStore {

  SingleEntity<User> currentUser();

  UserCollection users();

}
