package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class LobbyController implements Initializable{

  @FXML
  private Parent chatView;
  @FXML
  private ChatController chatViewController;

  @FXML
  private TextArea chatHistory;
  @FXML
  private TextField messageText;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //chatController.addMessageChat("test me bitch");
  }

  public LobbyController getController(){
    return this;
  }

  public ChatController getChatController() {
    return this.chatViewController;
  }

  public void testStuff(){
    System.out.println("i am here");
    chatViewController.addMessageChat("hello from here");
    //chatController.addMessageChat("test");
    //chatHistory.appendText("heyy");
  }
}
