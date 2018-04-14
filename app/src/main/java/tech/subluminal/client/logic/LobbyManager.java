package tech.subluminal.client.logic;

import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.shared.net.Connection;

/**
 * Manages the chat/game lobbies on the client side.
 */
public class LobbyManager {

  private final LobbyStore lobbyStore;
  private final Connection connection;

  public LobbyManager(LobbyStore lobbyStore, Connection connection) {
    this.lobbyStore = lobbyStore;
    this.connection = connection;
  }

  //TODO: React to packeage from connection
  //TODO: Presenter/Delegate: UserMangager
  //TODO: Take requests from Presenter

}
