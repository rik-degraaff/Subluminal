package tech.subluminal.server.stores;

import tech.subluminal.shared.stores.IdentifiableCollection;
import tech.subluminal.shared.stores.records.User;

/**
 * Stores server-side information about the users.
 */
public interface UserStore extends ReadOnlyUserStore {

  IdentifiableCollection<User> connectedUsers();
}
