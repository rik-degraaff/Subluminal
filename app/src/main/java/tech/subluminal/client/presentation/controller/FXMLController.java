package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {

  @FXML
  private TextArea chatHistory;
  @FXML
  private TextField messageText;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    chatHistory.setText("I'm a Chat.\n");
  }

  public void sendMessage(String message) {
    chatHistory.appendText(message + "\n");
  }

  public void sendMessage(ActionEvent actionEvent) {
    String message = messageText.getText();
    if(!message.equals("")){chatHistory.appendText( message + "\n");}
  }

  public void addMessageChat(String message, String username) {
    chatHistory.appendText(username + ": " + message + "\n");
  }

  public void addMessageChat(String message) {
    chatHistory.appendText(message + "\n");
  }

  public FXMLController getController(){
    return this.getController();
  }
}
