package tech.subluminal.server.stores;

import java.util.HashMap;
import java.util.Map;
import tech.subluminal.server.stores.LobbyUserStore;
import tech.subluminal.shared.records.User;

/**
 * Saves server-side information which user has has joined what lobby.
 */
public class InMemoryLobbyUserStore implements LobbyUserStore {

  private Map<String, User> userMap = new HashMap<>();

  public InMemoryLobbyUserStore() {
  }

  @Override
  public void addUser(User user) {

  }

  @Override
  public void updateUser(User user) {

  }

  @Override
  public void removeUserByID(String id) {

  }
}
