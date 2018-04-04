package tech.subluminal.server.stores;

import tech.subluminal.server.stores.UserStore;
import tech.subluminal.shared.records.User;

/**
 * Server as user store associated to a specific lobby
 */
public interface LobbyUserStore extends UserStore {

  @Override
  default void addUser(User user) {

  }

  @Override
  default void updateUser(User user) {

  }

  @Override
  default void removeUserByID(String id) {

  }
}
