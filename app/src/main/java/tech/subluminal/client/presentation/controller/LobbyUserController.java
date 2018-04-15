package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.LobbyPresenter;
import tech.subluminal.client.presentation.LobbyPresenter.Delegate;


public class LobbyUserController implements Initializable{

  @FXML
  private AnchorPane lobbyHost;

  @FXML
  private AnchorPane playerList;

  @FXML
  private AnchorPane lobbySettings;

  private LobbyPresenter.Delegate lobbyDelegate;

  public void setLobbyDelegate(Delegate lobbyDelegate) {
    this.lobbyDelegate = lobbyDelegate;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

}
