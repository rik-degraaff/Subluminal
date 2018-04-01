package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LobbyController implements Initializable{

  @FXML
  private ChatController chatController;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //chatController.addMessageChat("test me bitch");
  }

  public LobbyController getController(){
    return this;
  }


}
