package tech.subluminal.server.stores;

import tech.subluminal.shared.stores.ReadOnlyIdentifiableCollection;
import tech.subluminal.shared.stores.records.User;

/**
 * Server-side information about users can be retrieved from this store.
 */
public interface ReadOnlyUserStore {

  ReadOnlyIdentifiableCollection<User> connectedUsers();
}
