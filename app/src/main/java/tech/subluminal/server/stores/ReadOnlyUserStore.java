package tech.subluminal.server.stores;

import java.util.Collection;
import java.util.Optional;
import tech.subluminal.shared.records.User;

/**
 * Server-side information about users can be retrieved from this store.
 */
public interface ReadOnlyUserStore {

  /**
   * @return all the users that are connected to the server.
   */
  Collection<User> getUsers();

  /**
   * Retrieves a user identified by a given id.
   *
   * @param id the id of the requested user.
   * @return the user, empty when not found.
   */
  Optional<User> getUserByID(String id);
}
