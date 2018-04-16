package tech.subluminal.client.presentation.customElements;

import javafx.scene.Group;
import tech.subluminal.client.presentation.LobbyPresenter;

public class LobbyComponent extends Group implements LobbyPresenter {

  private LobbyListComponent lobbyList;
  private LobbyUserComponent lobbyUser;
  private LobbyPresenter.Delegate lobbyDelegate;

  public LobbyComponent(LobbyListComponent lobbyList, LobbyUserComponent lobbyUser) {

    this.lobbyList = lobbyList;
    this.lobbyUser = lobbyUser;

    this.getChildren().add(lobbyList);
  }

  public void setListActive() {
    if(this.getChildren().contains(lobbyUser)){
      this.getChildren().remove(lobbyUser);
    }
    this.getChildren().add(lobbyList);
  }

  public void setUserActive() {
    if(this.getChildren().contains(lobbyList)){
      this.getChildren().remove(lobbyList);
    }
    this.getChildren().add(lobbyUser);
  }

  public void onLobbyJoin(String id) {
    System.out.println("join lobby: " + id);
    //lobbyDelegate.joinLobby(id);
  }

  @Override
  public void joinLobbySucceded() {
    setUserActive();
  }

  @Override
  public void leaveLobbySucceded() {
    setListActive();
  }

  @Override
  public void createLobbySucceded() {

  }

  @Override
  public void lobbyListReceived() {

  }

  @Override
  public void setLobbyDelegate(Delegate delegate) {
    this.lobbyDelegate = delegate;
  }
}
