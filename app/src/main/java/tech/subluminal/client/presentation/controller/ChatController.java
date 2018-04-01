package tech.subluminal.client.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private VBox chatBox;

    @FXML
    private TextArea chatHistory;
    @FXML
    private TextField messageText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatHistory.setText("I'm a Chat.\n");
    }


    public ChatController getController(){
        return this;
    }

    public void sendMessage(String message) {
        chatHistory.appendText("~/" + message + "\n");
    }

    public void sendMessage(ActionEvent actionEvent) {
        String message = messageText.getText();
        if(!message.equals("")){
            sendMessage(message);
            messageText.setText("");
        }
    }

    public void addMessageChat(String message, String username) {
        chatHistory.appendText(username + ": " + message + "\n");
    }

    public void addMessageChat(String message) {
        chatHistory.appendText(message + "\n");
    }
}
