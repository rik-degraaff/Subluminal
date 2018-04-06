package tech.subluminal.server.stores;


import tech.subluminal.shared.records.Lobby;
import tech.subluminal.shared.stores.IdentifiableCollection;

/**
 * Saves server-side information about open lobbies.
 */
public interface LobbyStore {

  LobbyCollection lobbies();
}
