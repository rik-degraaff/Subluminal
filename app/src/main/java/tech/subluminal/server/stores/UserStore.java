package tech.subluminal.server.stores;

import java.util.Collection;
import java.util.Optional;
import tech.subluminal.shared.records.User;

/**
 * Stores server-side information about the users.
 */
public interface UserStore extends ReadOnlyUserStore {

  /**
   * Adds a user to the user store.
   *
   * @param user the user to add.
   */
  void addUser(User user);

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
