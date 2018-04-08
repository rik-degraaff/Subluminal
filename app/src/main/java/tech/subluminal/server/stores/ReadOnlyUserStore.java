package tech.subluminal.server.stores;

import java.util.Collection;
import java.util.Optional;
import tech.subluminal.shared.stores.ReadOnlyIdentifiableCollection;
import tech.subluminal.shared.stores.records.User;
import tech.subluminal.shared.util.Synchronized;

/**
 * Server-side information about users can be retrieved from this store.
 */
public interface ReadOnlyUserStore {

  ReadOnlyIdentifiableCollection<User> connectedUsers();
}
