package tech.subluminal.server.stores;


/**
 * Saves server-side information about open lobbies.
 */
public interface LobbyStore {

  LobbyCollection lobbies();
}
