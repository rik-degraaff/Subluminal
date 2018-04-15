package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class LobbyUserController implements Initializable, Observer {

  @FXML
  private AnchorPane lobbyHost;

  @FXML
  private AnchorPane playerList;

  @FXML
  private AnchorPane lobbySettings;

  private MainController main;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

  @Override
  public void setMainController(MainController main) {
    this.main = main;
  }
}
