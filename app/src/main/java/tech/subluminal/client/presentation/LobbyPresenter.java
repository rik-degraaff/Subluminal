package tech.subluminal.client.presentation;

public interface LobbyPresenter {

  void joinLobbySucceded();

  void leaveLobbySucceded();

  void createLobbySucceded();

  void lobbyListReceived();

  void lobbyUpdateReceived();

  void setLobbyDelegate(Delegate delegate);

  interface Delegate {

    void joinLobby(String id);

    void leaveLobby();

    void createLobby(String name);

    void getLobbyList();

    void startGame();

    void startTutorial();

    void joinGame(String lobbyID);
  }
}
