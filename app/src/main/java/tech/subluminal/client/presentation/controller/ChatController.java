package tech.subluminal.client.presentation.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tech.subluminal.client.presentation.ChatPresenter;
import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.client.stores.ReadOnlyUserStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.records.Channel;
import tech.subluminal.shared.stores.records.User;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements ChatPresenter, UserPresenter, Initializable {

    @FXML
    private VBox chatBox;
    @FXML
    private ListView<Label> chatHistory;
    @FXML
    private TextField messageText;
    @FXML
    private CheckBox isGlobalShown;

    private ReadOnlyUserStore userStore;
    private ChatPresenter.Delegate chatDelegate;
    private UserPresenter.Delegate userDelegate;

    private List<Label> historyMessages = new LinkedList<>();

    private ObservableList<Label> chatList = FXCollections.observableArrayList();
    private FilteredList<Label> filteredList = new FilteredList<>(chatList);

    public ChatController getController() {
        return this;
    }

    public void setUserStore(UserStore store) {
        this.userStore = store;
    }

    public void addMessageChat(String message, Channel channel) {
        Platform.runLater(() -> {
            Label msg = new Label(message);
            msg.setWrapText(true);

            msg.getStyleClass().add(channel.toString().toLowerCase() + "-message");
            chatList.add(msg);


            scrollToBottom();

        });
    }

    public void addMessageChat(String message, String username, Channel channel) {
        addMessageChat(username + ": " + message, channel);
    }

    public void updateFilter(ActionEvent e) {
        if (isGlobalShown.isSelected()) {
            filteredList.setPredicate(l -> true);
        } else {
            filteredList.setPredicate(l -> !l.getStyleClass().contains("global-message"));
        }

    }

    private void scrollToBottom() {
        chatHistory.scrollTo(chatHistory.getItems().size() - 1);
    }

    public void sendMessage(ActionEvent actionEvent) {
        String line = messageText.getText();
        if (!line.equals("")) {
            char command = line.charAt(0);

            if (command == '@') {
                handleDirectedChatMessage(line);
            } else if (command == '/') {
                handleCommand(line);
            } else {
                //send @all
                chatDelegate.sendGlobalMessage(line);
                addMessageChat("you: " + line, Channel.GLOBAL);
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
            addMessageChat("You did not enter a new username, I got you covered, fam.", Channel.SERVER);
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
            addMessageChat("you: " + message, Channel.GLOBAL);
            clearInput();
        } else if (channel.equals("game")) {
            //send @game
            chatDelegate.sendGameMessage(message);
            addMessageChat("you: " + message, Channel.GAME);
            clearInput();
        } else {
            //send @player
            if (userStore.users().getByUsername(channel).use(users -> !users.isEmpty())) {

                chatDelegate.sendWhisperMessage(message, channel);
                addMessageChat("you@" + channel + ": " + message, Channel.WHISPER);
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


    public void clearInput() {
        messageText.setText("");
    }

    @Override
    public void displaySystemMessage(String message) {
        addMessageChat(message, Channel.SYSTEM);
    }

    /**
     * Fired when a someone sends a message to all users on the server.
     *
     * @param message  is the text of the message.
     * @param username from the sender of the message.
     */
    @Override
    public void globalMessageReceived(String message, String username) {
        addMessageChat(message, username, Channel.GLOBAL);
    }

    /**
     * Fired when a personal message is received.
     *
     * @param message  of the received whisper.
     * @param username of the sender.
     */
    @Override
    public void whisperMessageReceived(String message, String username) {
        addMessageChat(message, username, Channel.WHISPER);
    }

    /**
     * Fired when a message from the same game is received.
     *
     * @param message  of the game message.
     * @param username fo the sender.
     */
    @Override
    public void gameMessageReceived(String message, String username) {
        addMessageChat(message, username, Channel.GAME);
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
        addMessageChat("Succesfully logged in as: " + getCurrentUsername(), Channel.SERVER);
    }

    private String getCurrentUsername() {
        return userStore.currentUser().get().use(user -> user.map(User::getUsername))
                .orElseThrow(() -> new IllegalStateException("Current User is not in the Userstore."));
    }

    /**
     * Gets called when the client got logged out.
     */
    @Override
    public void logoutSucceeded() {
        addMessageChat("Successfully logged out!", Channel.SERVER);
    }

    /**
     * Fired when a username got successfully changed.
     */
    @Override
    public void nameChangeSucceeded() {
        addMessageChat("Your username got changed to: " + getCurrentUsername(), Channel.SERVER);
    }

    @Override
    public void setUserDelegate(UserPresenter.Delegate delegate) {
        this.userDelegate = delegate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatHistory.setItems(filteredList);
    }
}
