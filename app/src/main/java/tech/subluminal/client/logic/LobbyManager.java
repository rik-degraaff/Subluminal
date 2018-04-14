package tech.subluminal.client.logic;

import main.java.tech.subluminal.shared.messages.LobbyLeaveRes;
import tech.subluminal.client.presentation.LobbyPresenter;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.shared.messages.LobbyJoinReq;
import tech.subluminal.shared.net.Connection;

/**
 * Manages the chat/game lobbies on the client side.
 */
public class LobbyManager implements LobbyPresenter.Delegate {

  private final LobbyStore lobbyStore;
  private final Connection connection;

  public LobbyManager(LobbyStore lobbyStore, Connection connection) {
    this.lobbyStore = lobbyStore;
    this.connection = connection;
  }

  @Override
  public void joinLobby(String id) {
    connection.sendMessage(new LobbyJoinReq(id));
  }

  @Override
  public void leaveLobby() {
    connection.sendMessage(new LobbyLeaveRes());

  }

  @Override
  public void createLobby(String name) {
    //TODO: implement this

  }

  @Override
  public void getLobbyList() {
    //TODO: implements this
  }

  //TODO: React to packeage from connection
  //TODO: Presenter/Delegate: UserMangager
  //TODO: Take requests from Presenter

}
