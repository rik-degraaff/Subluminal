package tech.subluminal.client.stores;

import java.util.Optional;
import tech.subluminal.shared.stores.SingleEntity;
import tech.subluminal.shared.stores.records.User;
import tech.subluminal.shared.util.Synchronized;

/**
 * Stores client-side information about the users.
 */
public interface UserStore extends ReadOnlyUserStore {

  SingleEntity<User> currentUser();

  UserCollection users();

  Synchronized<Optional<String>> reconnectID();
}
