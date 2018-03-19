package tech.subluminal.server.stores;

import java.util.Optional;
import java.util.Set;
import tech.subluminal.shared.records.User;

/**
 * Stores server-side information about the users.
 */
public interface UserStore {

  /**
   * @return all the users that are connected to the server.
   */
  Set<User> getUsers();

  /**
   * Adds a user to the user store.
   *
   * @param user the user to add.
   */
  void addUser(User user);

  /**
   * Retrieves a user identified by a given id.
   *
   * @param id the id of the requested user.
   * @return the user, empty when not found.
   */
  Optional<User> getUserByID(String id);

  /**
   * Updates the user identified by user.userID.
   *
   * @param user the user to update.
   */
  void updateUser(User user);

  /**
   * Remooves a user identified by a given id from the user store.
   *
   * @param id the id of the user that should be removed.
   */
  void removeUserByID(String id);
}
