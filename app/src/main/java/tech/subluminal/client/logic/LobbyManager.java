package tech.subluminal.client.logic;

import tech.subluminal.client.presentation.LobbyPresenter;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.shared.messages.LobbyJoinReq;
import tech.subluminal.shared.messages.GameStartRes;
import tech.subluminal.shared.messages.LobbyJoinRes;
import tech.subluminal.shared.messages.LobbyLeaveRes;
import tech.subluminal.shared.messages.LobbyListRes;
import tech.subluminal.shared.messages.LobbyUpdateRes;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.Lobby;

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
  private void attachHandlers() {
    connection
        .registerHandler(LobbyJoinRes.class, LobbyJoinRes::fromSON, this::onLobbyJoin);
    connection
        .registerHandler(LobbyLeaveRes.class, LobbyLeaveRes::fromSON, this::onLobbyLeave);
    connection
        .registerHandler(LobbyListRes.class, LobbyListRes::fromSON, this::onLobbyList);
    connection
        .registerHandler(LobbyUpdateRes.class, LobbyUpdateRes::fromSON, this::onLobbyUpdate);
    connection
        .registerHandler(GameStartRes.class, GameStartRes::fromSON, this::onGameStart);
    //connection
    //    .registerHandler(LobbyCreateRes.class, LobbyCreateRes::fromSON, this::onLobbyCreate);
  }

  private void onGameStart(GameStartRes res) {
    //TODO: Start game
  }

  //private void onLobbyCreate(LobbyCreateRes res) {
  //
  //}

  private void onLobbyList(LobbyListRes res) {
    lobbyStore.lobbies().get().consume(opt -> opt.ifPresent(list -> {
      list.clear();
      list.addAll(res.getSlimLobbies());
    }));
  }

  private void onLobbyUpdate(LobbyUpdateRes res) {
    lobbyStore.currentLobby().set(res.getLobby());
  }

  private void onLobbyLeave(LobbyLeaveRes res) {
    lobbyStore.currentLobby().remove();
  }

  private void onLobbyJoin(LobbyJoinRes res) {
    Lobby lobby = res.getLobby();
    lobbyStore.currentLobby().set(lobby);
  }
}
