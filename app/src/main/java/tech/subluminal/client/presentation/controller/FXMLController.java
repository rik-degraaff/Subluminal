package tech.subluminal.client.presentation.controller;

import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class FXMLController implements Initializable {

  @FXML
  private TextArea chatHistory;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    chatHistory.setText("I'm a Label.");
  }

  public void addToChat(String message){
    chatHistory.appendText(message);
  }
}
