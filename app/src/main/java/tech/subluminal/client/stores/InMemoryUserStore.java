package tech.subluminal.client.stores;

import static tech.subluminal.shared.util.function.IfPresent.ifPresent;

import java.util.Optional;
import java.util.prefs.Preferences;
import tech.subluminal.shared.stores.SingleEntity;
import tech.subluminal.shared.stores.records.User;
import tech.subluminal.shared.util.RemoteSynchronized;
import tech.subluminal.shared.util.Synchronized;

/**
 * Stores client-side information about the users in memory.
 */
public class InMemoryUserStore implements UserStore {

  static private final String RECONNECT_ID_KEY = "reconnectID";

  private final SingleEntity<User> currentUser = new SingleEntity<>();
  private final UserCollection users = new UserCollection();
  private final Synchronized<Optional<String>> reconnectID = new RemoteSynchronized<>(
      InMemoryUserStore::getReconnectIDFromPrefs,
      InMemoryUserStore::writeReconnectIDToPrefs);

  private static Optional<String> getReconnectIDFromPrefs() {
    return Optional.ofNullable(Preferences.userRoot().get(RECONNECT_ID_KEY, null));
  }

  private static void writeReconnectIDToPrefs(Optional<String> optID) {
    ifPresent(optID)
        .then(id -> Preferences.userRoot().put(RECONNECT_ID_KEY, id))
        .els(() -> Preferences.userRoot().remove(RECONNECT_ID_KEY));
  }

  @Override
  public SingleEntity<User> currentUser() {
    return currentUser;
  }

  @Override
  public UserCollection users() {
    return users;
  }

  @Override
  public Synchronized<Optional<String>> reconnectID() {
    return reconnectID;
  }
}
