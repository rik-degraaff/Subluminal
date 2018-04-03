package tech.subluminal.client.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import tech.subluminal.client.presentation.ChatPresenter;
import tech.subluminal.client.presentation.ChatPresenter.Delegate;
import tech.subluminal.client.presentation.UserPresenter;

public class ChatController implements ChatPresenter, UserPresenter{

    @FXML
    private VBox chatBox;

    @FXML
    private TextArea chatHistory;
    @FXML
    private TextField messageText;


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

    /**
     * Fired when a someone sends a message to all users on the server.
     *
     * @param message is the text of the message.
     * @param username from the sender of the message.
     */
    @Override
    public void globalMessageReceived(String message, String username) {

    }

    /**
     * Fired when a personal message is received.
     *
     * @param message of the received whisper.
     * @param username of the sender.
     */
    @Override
    public void whisperMessageReceived(String message, String username) {

    }

    /**
     * Fired when a message from the same game is received.
     *
     * @param message of the game message.
     * @param username fo the sender.
     */
    @Override
    public void gameMessageReceived(String message, String username) {

    }

    /**
     * Sets the delegate who gets fired by user messages.
     *
     * @param delegate is the delegate which will be set.
     */
    @Override
    public void setChatDelegate(ChatPresenter.Delegate delegate) {

    }

    /**
     * Function that should be called when login succeeded.
     */
    @Override
    public void loginSucceeded() {
        addMessageChat("Succesfully logged in!");
        System.out.println("logged in");
    }

    /**
     * Gets called when the client got logged out.
     */
    @Override
    public void logoutSucceeded() {

    }

    /**
     * Fired when a username got successfully changed.
     */
    @Override
    public void nameChangeSucceeded() {

    }

    @Override
    public void setUserDelegate(UserPresenter.Delegate delegate) {

    }
}
