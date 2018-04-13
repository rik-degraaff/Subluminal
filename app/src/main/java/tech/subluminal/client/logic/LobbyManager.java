package tech.subluminal.client.logic;

import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.shared.messages.LobbyJoinReq;
import tech.subluminal.shared.messages.LobbyJoinRes;
import tech.subluminal.shared.messages.LobbyLeaveReq;
import tech.subluminal.shared.messages.LobbyLeaveRes;
import tech.subluminal.shared.messages.LobbyListReq;
import tech.subluminal.shared.messages.LobbyListRes;
import tech.subluminal.shared.messages.LobbyUpdate;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Lobby;

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

  private void attachHandlers() {
    connection.registerHandler(LobbyJoinRes.class, LobbyJoinRes::fromSON, this::onLobbyJoin);
    connection.registerHandler(LobbyLeaveRes.class, LobbyLeaveRes::fromSON, this::onLobbyLeave);
    //connection.registerHandler(LobbyListRes.class, LobbyListRes::fromSON, this::onLobbyList); //TODO: implement message
    //connection.registerHandler(LobbyUpdate.class, LobbyUpdate::fromSON, this::onLobbyUpdate); //TODO: implement message
  }

  private void onLobbyUpdate(LobbyUpdate res) {

  }

  private void onLobbyListRes(LobbyListRes res) {

  }

  private void onLobbyLeaveRes(LobbyLeaveRes res) {
    lobbyStore.currentLobby().remove();
  }

  private void onLobbyJoinRes(LobbyJoinRes res) {
    Lobby lobby = res.getLobby();
    lobbyStore.currentLobby().set(lobby);
  }

  //TODO: React to packeage from connection
  //TODO: Presenter/Delegate: UserMangager
  //TODO: Take requests from Presenter

}
