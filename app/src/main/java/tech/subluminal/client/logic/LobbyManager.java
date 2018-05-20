package tech.subluminal.client.logic;

import javafx.application.Platform;
import tech.subluminal.client.presentation.LobbyPresenter;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.presentation.customElements.LobbyComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.shared.messages.GameStartReq;
import tech.subluminal.shared.messages.GameStartRes;
import tech.subluminal.shared.messages.LobbyCreateReq;
import tech.subluminal.shared.messages.LobbyJoinReq;
import tech.subluminal.shared.messages.LobbyJoinRes;
import tech.subluminal.shared.messages.LobbyLeaveReq;
import tech.subluminal.shared.messages.LobbyLeaveRes;
import tech.subluminal.shared.messages.LobbyListReq;
import tech.subluminal.shared.messages.LobbyListRes;
import tech.subluminal.shared.messages.LobbyUpdateRes;
import tech.subluminal.shared.messages.SpectateGameReq;
import tech.subluminal.shared.messages.StartTutorialReq;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.Lobby;

/**
 * Manages the chat/game lobbies on the client side.
 */
public class LobbyManager implements LobbyPresenter.Delegate {

  private final LobbyStore lobbyStore;
  private final Connection connection;
  private final LobbyPresenter lobbyPresenter;
  private final MainController mainController;

  /**
   * Initializes the lobbymanager.
   * @param lobbyStore is the data structure to store all the created lobbies on the server.
   * @param connection holds the socket connection to the server.
   * @param lobbyPresenter the component to show the lobbies in.
   * @param mainController connects all the controllers and handles the communication between them.
   */
  public LobbyManager(LobbyStore lobbyStore, Connection connection,
      LobbyComponent lobbyPresenter, MainController mainController) {
    this.mainController = mainController;
    this.lobbyStore = lobbyStore;
    this.connection = connection;
    this.lobbyPresenter = lobbyPresenter;

    lobbyPresenter.setLobbyDelegate(this);

    attachHandlers();
  }

  @Override
  public void joinLobby(String id) {
    connection.sendMessage(new LobbyJoinReq(id));
  }

  @Override
  public void leaveLobby() {
    connection.sendMessage(new LobbyLeaveReq());

  }

  @Override
  public void createLobby(String name) {
    connection.sendMessage(new LobbyCreateReq(name));

  }

  @Override
  public void getLobbyList() {
    connection.sendMessage(new LobbyListReq());
  }

  @Override
  public void startGame() {
    connection.sendMessage(new GameStartReq());
  }

  @Override
  public void startTutorial() {
    connection.sendMessage(new StartTutorialReq());
  }

  @Override
  public void joinGame(String lobbyID) {
    connection.sendMessage(new SpectateGameReq(lobbyID));
  }


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
  }

  private void onGameStart(GameStartRes res) {
    mainController.onMapOpenHandle();
  }


  private void onLobbyList(LobbyListRes res) {
    Platform.runLater(() -> {
      lobbyStore.lobbies().get().consume(opt -> opt.ifPresent(list -> {
        list.clear();
        list.addAll(res.getSlimLobbies());
      }));
    });

  }

  private void onLobbyUpdate(LobbyUpdateRes res) {
    lobbyStore.currentLobby().set(res.getLobby());

    lobbyPresenter.lobbyUpdateReceived();
  }

  private void onLobbyLeave(LobbyLeaveRes res) {
    lobbyStore.currentLobby().remove();

    lobbyPresenter.leaveLobbySucceded();
  }

  private void onLobbyJoin(LobbyJoinRes res) {
    Lobby lobby = res.getLobby();
    lobbyStore.currentLobby().set(lobby);

    lobbyPresenter.joinLobbySucceded();
    lobbyPresenter.lobbyUpdateReceived();
  }
}
