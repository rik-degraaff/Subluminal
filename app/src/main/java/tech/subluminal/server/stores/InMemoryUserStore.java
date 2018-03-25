package tech.subluminal.server.stores;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import tech.subluminal.shared.records.User;

/**
 * Stores server-side information about the users in memory.
 */
public class InMemoryUserStore implements UserStore {

  private Map<String, User> userMap = new HashMap<>();

  /**
   * @return all the users that are connected to the server.
   */
  @Override
  public Collection<User> getUsers() {
    return userMap.values();
  }

  /**
   * Adds a user to the user store.
   *
   * @param user the user to add.
   */
  @Override
  public void addUser(User user) {
    userMap.put(user.getId(), user);
  }

  /**
   * Retrieves a user identified by a given id.
   *
   * @param id the id of the requested user.
   * @return the user, empty when not found.
   */
  @Override
  public Optional<User> getUserByID(String id) {
    return Optional.ofNullable(userMap.get(id));
  }

  /**
   * Updates the user identified by user.userID.
   *
   * @param user the user to update.
   */
  @Override
  public void updateUser(User user) {
    userMap.replace(user.getId(), user);
  }

  /**
   * Removes a user identified by a given id from the user store.
   *
   * @param id the id of the user that should be removed.
   */
  @Override
  public void removeUserByID(String id) {
    userMap.remove(id);
  }
}
