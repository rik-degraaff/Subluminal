package tech.subluminal.client.presentation.customElements;

import javafx.beans.property.StringProperty;
import javafx.scene.Group;

public class LobbyComponent extends Group {

  private LobbyListComponent lobbyList;
  private LobbyUserComponent lobbyUser;

  public LobbyComponent(LobbyListComponent lobbyList, LobbyUserComponent lobbyUser){

    this.lobbyList = lobbyList;
    this.lobbyUser = lobbyUser;

    this.getChildren().add(lobbyList);
  }

  public void setListActive(){
    this.getChildren().remove(lobbyUser);
    this.getChildren().add(lobbyList);
  }

  public void setUserActive(){
    this.getChildren().remove(lobbyList);
    this.getChildren().add(lobbyUser);
  }

  public void onLobbyJoin(String id) {
    System.out.println("join lobby: " + id);
  }
}
