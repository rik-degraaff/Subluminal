package tech.subluminal.server.stores;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import tech.subluminal.shared.records.User;
import tech.subluminal.shared.util.Synchronized;

/**
 * Stores server-side information about the users in memory.
 */
public class InMemoryUserStore implements UserStore {

  private Synchronized<Map<String, Synchronized<User>>> userMap = new Synchronized<>(new HashMap<>());

  /**
   * Returns the users that are connected to the server.
   *
   * @return all the users that are connected to the server.
   */
  @Override
  public Synchronized<Collection<Synchronized<User>>> getUsers() {
    return userMap.map(Map::values);
  }

  /**
   * Adds a user to the user store.
   *
   * @param user the user to add.
   */
  @Override
  public void addUser(User user) {
    userMap.use(map -> map.put(user.getId(), new Synchronized<>(user)));
  }

  /**
   * Retrieves a user identified by a given id.
   *
   * @param id the id of the requested user.
   * @return the user, empty when not found.
   */
  @Override
  public Optional<Synchronized<User>> getUserByID(String id) {
    return Optional.ofNullable(userMap.use(map -> map.get(id)));
  }

  /**
   * Removes a user identified by a given id from the user store.
   *
   * @param id the id of the user that should be removed.
   */
  @Override
  public void removeUserByID(String id) {
    userMap.use(map -> map.remove(id));
  }
}
