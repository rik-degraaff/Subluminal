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
import tech.subluminal.client.stores.ReadOnlyUserStore;
import tech.subluminal.client.stores.UserStore;

public class ChatController implements ChatPresenter, UserPresenter, Initializable{

    @FXML
    private VBox chatBox;
    @FXML
    private TextArea chatHistory;
    @FXML
    private TextField messageText;

    private ReadOnlyUserStore userStore;
    private ChatPresenter.Delegate chatDelegate;
    private UserPresenter.Delegate userDelegate;


    public ChatController getController(){
        return this;
    }

    public void setUserStore(UserStore store){
        this.userStore = store;
    }

    public void sendMessage(String message) {
        chatHistory.appendText("~/" + message + "\n");
    }

    public void sendMessage(ActionEvent actionEvent) {
        String line = messageText.getText();
        if(!line.equals("")){
            char command = line.charAt(0);

            if (command == '@') {
                handleDirectedChatMessage(line);
            } else if (command == '/') {
                handleCommand(line);
            } else {
                //send @all
                chatDelegate.sendGlobalMessage(line);
                addMessageChat("you: " + line);
                clearInput();
            }
        }
    }

    /**
     * Handles all possible commands input by the user.
     *
     * @param line is the whole command input by the user.
     */
    private void handleCommand(String line) {
        //send /cmd
        String channel = getSpecifier(line);
        if (channel.equals("logout")) {
            userDelegate.logout();
        } else if (channel.equals("changename")) {
            handleNameChangeCmd(line, channel);
            clearInput();
        } else if (channel.equals("changelobby")) {
            //TODO: add functionality to change lobby
        }
    }

    private void handleNameChangeCmd(String line, String channel) {
        String newUsername = extractMessageBody(line, channel);
        //removes all whitespaces //TODO: may change later
        String username = newUsername.replaceAll(" ", "");

        if (username.equals("")) {
            addMessageChat("You did not enter a new username, I got you covered, fam.");
            username = "ThisisPatrick!";
        }

        userDelegate.changeUsername(username);
    }

    private void handleDirectedChatMessage(String line) {
        String channel = getSpecifier(line);
        String message = extractMessageBody(line, channel);

        if (channel.equals("all") || channel.equals(("server"))) {
            //send @all

            chatDelegate.sendGlobalMessage(message);
            addMessageChat("you: " + message);
            clearInput();
        } else if (channel.equals("game")) {
            //send @game
            chatDelegate.sendGameMessage(message);
            addMessageChat("you: " + message);
            clearInput();
        } else {
            //send @player
            if (userStore.getUserByUsername(channel) != null) {

                chatDelegate.sendWhisperMessage(message, channel);
                addMessageChat("you@"+channel+ ": " + message);
                clearInput();
            }
        }
    }

    private String getSpecifier(String line) {
        return line.split(" ", 2)[0].substring(1).toLowerCase();
    }

    private String extractMessageBody(String line, String channel) {
        return line.substring(channel.length() + 1);
    }

    public void addMessageChat(String message, String username) {
        chatHistory.appendText(username + ": " + message + "\n");
    }


    public void addMessageChat(String message) {
        chatHistory.appendText(message + "\n");
    }

    public void clearInput(){
        messageText.setText("");
    }

    /**
     * Fired when a someone sends a message to all users on the server.
     *
     * @param message is the text of the message.
     * @param username from the sender of the message.
     */
    @Override
    public void globalMessageReceived(String message, String username) {
        addMessageChat(message, username);
    }

    /**
     * Fired when a personal message is received.
     *
     * @param message of the received whisper.
     * @param username of the sender.
     */
    @Override
    public void whisperMessageReceived(String message, String username) {
        addMessageChat(message, username);
    }

    /**
     * Fired when a message from the same game is received.
     *
     * @param message of the game message.
     * @param username fo the sender.
     */
    @Override
    public void gameMessageReceived(String message, String username) {
        addMessageChat(message, username);
    }

    /**
     * Sets the delegate who gets fired by user messages.
     *
     * @param delegate is the delegate which will be set.
     */
    @Override
    public void setChatDelegate(ChatPresenter.Delegate delegate) {
        this.chatDelegate = delegate;
    }

    /**
     * Function that should be called when login succeeded.
     */
    @Override
    public void loginSucceeded() {
        addMessageChat("Succesfully logged in as: " + userStore.getCurrentUser().getUsername());
    }

    /**
     * Gets called when the client got logged out.
     */
    @Override
    public void logoutSucceeded() {
        addMessageChat("Successfully logged out!");
    }

    /**
     * Fired when a username got successfully changed.
     */
    @Override
    public void nameChangeSucceeded() {
        addMessageChat("Your username got changed to: " + userStore.getCurrentUser().getUsername());
    }

    @Override
    public void setUserDelegate(UserPresenter.Delegate delegate) {
        this.userDelegate = delegate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatHistory.setWrapText(true);
    }
}
