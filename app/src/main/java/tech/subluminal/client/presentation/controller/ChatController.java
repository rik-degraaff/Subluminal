package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import tech.subluminal.client.presentation.ChatPresenter;
import tech.subluminal.client.presentation.UserPresenter;
import tech.subluminal.client.stores.ReadOnlyUserStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.server.stores.records.HighScore;
import tech.subluminal.shared.records.Channel;
import tech.subluminal.shared.stores.records.User;


public class ChatController implements ChatPresenter, UserPresenter, Initializable {

  @FXML
  private ListView<Label> chatHistory;
  @FXML
  private TextField messageText;
  @FXML
  private CheckBox isGlobalShown;

  @FXML
  private Button sendButton;
  @FXML
  private Button sendAllButton;

  @FXML
  private AnchorPane chatBox;

  @FXML
  private Box chat3DBox;

  @FXML
  private Box sendBox;

  @FXML
  private Box sendAllBox;

  @FXML
  private GridPane sendOptions;

  private ReadOnlyUserStore userStore;
  private ChatPresenter.Delegate chatDelegate;
  private UserPresenter.Delegate userDelegate;

  private ObservableList<Label> chatList = FXCollections.observableArrayList();
  private FilteredList<Label> filteredList = new FilteredList<>(chatList);
  private MainController main;

  private BooleanProperty inGame = new SimpleBooleanProperty();

  public ChatController getController() {
    return this;
  }

  public void setMainController(MainController main) {
    this.main = main;
  }


  public boolean isInGame() {
    return inGame.get();
  }

  public void setInGame(boolean inGame) {
    this.inGame.set(inGame);
  }

  public BooleanProperty inGameProperty() {
    return inGame;
  }

  public void setUserStore(UserStore store) {
    this.userStore = store;
  }

  /**
   * Passes message to the channel formatter.
   * @param message the message to send.
   * @param channel the channel to send the message in.
   */
  public void addMessageChat(String message, Channel channel) {
    Platform.runLater(() -> {
      Label msg = new Label(message);
      msg.setWrapText(true);
      Insets padding = chatHistory.getPadding();
      msg.maxWidthProperty().bind(Bindings.createDoubleBinding(
          () -> chatHistory.getWidth() - padding.getLeft() - padding.getRight() - 1,
          chatHistory.widthProperty(), chatHistory.paddingProperty()));
      //msg.prefWidthProperty().bind(chatHistory.widthProperty());

      msg.getStyleClass().add(channel.toString().toLowerCase() + "-message");
      chatList.add(msg);

      scrollToBottom();

    });
  }

  /**
   * Formats the whisper message and prints it to the chat.
   * @param message the message to send.
   * @param username the user who sent the message.
   * @param channel the channel to send the message in.
   */
  public void addMessageChat(String message, String username, Channel channel) {
    if (channel == Channel.WHISPER) {
      addMessageChat(username + "@you: " + message, channel);
    } else if (channel == Channel.GAME) {
      addMessageChat(username + "@game: " + message, channel);
    } else {
      addMessageChat(username + ": " + message, channel);
    }
  }

  /**
   * Filters the global message from the chat view.
   * @param e changes the predicate for filtering.
   */
  public void updateFilter(ActionEvent e) {
    if (isGlobalShown.isSelected()) {
      filteredList.setPredicate(l -> true);
    } else {
      filteredList.setPredicate(l -> !l.getStyleClass().contains("global-message"));
    }

  }

  /**
   * Scrolls the chat view to bottom (last message).
   */
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
        chatDelegate.sendGameMessage(line);
        addMessageChat("you@game: " + line, Channel.GAME);
        clearInput();
      }
    }
  }

  /**
   * Send the message to all users.
   * @param actionEvent the trigger for when to the send message.
   */
  public void sendMessageAll(ActionEvent actionEvent) {
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
    } else if (channel.equals("scores")) {
      chatDelegate.requestHighScores();
      clearInput();
    }
  }

  /**
   * Sends a private message to a user.
   * @param recipiant is the user to receive the message.
   */
  public void writeAt(String recipiant) {
    String temp = messageText.getText();
    clearInput();
    if (temp == null) {
      messageText.setText("@" + recipiant + " ");
    } else if (temp.contains("@")) {
      String parts[] = temp.split(" ", 2);
      if (parts.length >= 2) {
        messageText.setText("@" + recipiant + " " + parts[1]);
      } else {
        messageText.setText("@" + recipiant + " ");
      }
    } else {
      messageText.setText("@" + recipiant + " " + temp);
    }
    messageText.requestFocus();
    messageText.selectPositionCaret(messageText.getText().length());
  }

  private void handleNameChangeCmd(String line, String channel) {
    String newUsername = extractMessageBody(line, channel);
    //removes all whitespaces //TODO: may change later
    String username = newUsername.replaceAll(" ", "");

    if (username.equals("")) {
      printPatrick();
      username = "ThisisPatrick!";
    }

    userDelegate.changeUsername(username);
  }

  public void printPatrick() {
    addMessageChat("You did not enter a new username, I got you covered, fam.", Channel.INFO);
  }

  public void changeName(String username) {
    userDelegate.changeUsername(username);
  }

  private void handleDirectedChatMessage(String line) {
    String specifier = getSpecifier(line);
    String channel = specifier.toLowerCase();
    String message = extractMessageBody(line, channel);

    if (channel.equals("all") || channel.equals(("server"))) {
      //send @all

      chatDelegate.sendGlobalMessage(message);
      addMessageChat("you: " + message, Channel.GLOBAL);
      clearInput();
    } else if (channel.equals("game")) {
      //send @game
      chatDelegate.sendGameMessage(message);
      addMessageChat("you@game: " + message, Channel.GAME);
      clearInput();
    } else {
      //send @player
      if (userStore.users().getByUsername(specifier).use(users -> !users.isEmpty())) {

        chatDelegate.sendWhisperMessage(message, specifier);
        addMessageChat("you@" + specifier + ": " + message, Channel.WHISPER);
        clearInput();
      }
    }
  }

  private String getSpecifier(String line) {
    return line.split(" ", 2)[0].substring(1);
  }

  private String extractMessageBody(String line, String channel) {
    return line.substring(channel.length() + 1);
  }

  public void requestHighscores() {
    chatDelegate.requestHighScores();
  }

  public void clearInput() {
    messageText.setText("");
  }

  @Override
  public void displaySystemMessage(String message) {
    addMessageChat(message, Channel.CRITICAL);

  }

  /**
   * Fired when a someone sends a message to all users on the server.
   *
   * @param message is the text of the message.
   * @param username from the sender of the message.
   */
  @Override
  public void globalMessageReceived(String message, String username) {
    addMessageChat(message, username, Channel.GLOBAL);
  }

  /**
   * Fired when a personal message is received.
   *
   * @param message of the received whisper.
   * @param username of the sender.
   */
  @Override
  public void whisperMessageReceived(String message, String username) {
    addMessageChat(message, username, Channel.WHISPER);
  }

  /**
   * Fired when a message from the same game is received.
   *
   * @param message of the game message.
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

  @Override
  public void updateHighscore(List<HighScore> highScores) {
    main.onUpdateHighscoreHandle(highScores);
  }

  /**
   * Function that should be called when login succeeded.
   */
  @Override
  public void loginSucceeded() {
    addMessageChat("Logged in as: " + getCurrentUsername(), Channel.INFO);
  }

  /**
   * Gets called when the client got logged out.
   */
  @Override
  public void logoutSucceeded() {
    addMessageChat("Successfully logged out!", Channel.INFO);
  }

  /**
   * Fired when a username got successfully changed.
   */
  @Override
  public void nameChangeSucceeded() {
    addMessageChat("Your username got changed to: " + getCurrentUsername(), Channel.INFO);
  }

  @Override
  public void setUserDelegate(UserPresenter.Delegate delegate) {
    this.userDelegate = delegate;
  }

  @Override
  public void onPlayerJoin(String username) {
    addMessageChat(username + " joined.", Channel.INFO);

  }

  @Override
  public void onPlayerLeave(String username) {
    addMessageChat(username + " left.", Channel.INFO);
  }

  @Override
  public void onPlayerUpdate(String oldUsername, String newUsername) {
    addMessageChat(oldUsername + " changed his name to: " + newUsername + ".", Channel.INFO);
  }

  private String getCurrentUsername() {
    return userStore.currentUser().get().use(user -> user.map(User::getUsername))
        .orElseThrow(() -> new IllegalStateException("Current User is not in the Userstore."));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    chatHistory.setItems(filteredList);
    chatHistory.setPadding(new Insets(0, 0, 0, 0));
    //  chatOptions.getChildren().remove(sendButton);

    chat3DBox.heightProperty().bind(chatBox.heightProperty());
    chat3DBox.widthProperty().bind(chatBox.widthProperty());

    //boardCylinder.heightProperty().bind(chatBox.widthProperty());
    PhongMaterial material = new PhongMaterial();
    material.setSpecularColor(Color.BLACK);
    material.setDiffuseColor(Color.GREY);

    PhongMaterial materialChat = new PhongMaterial();
    materialChat.setSpecularColor(Color.BLACK);
    materialChat.setDiffuseColor(Color.BEIGE);

    chat3DBox.setMaterial(materialChat);

    chatBox.setTranslateX(-5);

    sendAllBox.widthProperty().bind(sendOptions.widthProperty());
    sendBox.widthProperty().bind(sendOptions.widthProperty());
    Translate transAll = new Translate();
    //sendAllBox.getTransforms().add(transAll);
    sendAllButton.getTransforms().add(transAll);
    Scale scaleAll = new Scale();
    sendAllBox.getTransforms().add(scaleAll);

    Translate trans = new Translate();
    //sendBox.getTransforms().add(trans);
    sendButton.getTransforms().add(trans);
    Scale scale = new Scale();
    sendBox.getTransforms().add(scale);

    sendButton.setOnMouseEntered((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(trans.zProperty(), 3)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scale.zProperty(), 0.5)));
      timeTl.play();
    });
    sendButton.setOnMouseExited((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(trans.zProperty(), 0)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scale.zProperty(), 1)));
      timeTl.play();
    });
    sendAllButton.setOnMouseEntered((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(transAll.zProperty(), 3)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scaleAll.zProperty(), 0.5)));
      timeTl.play();
    });
    sendAllButton.setOnMouseExited((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(transAll.zProperty(), 0)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scaleAll.zProperty(), 1)));
      timeTl.play();
    });

    sendBox.setMaterial(material);
    sendAllBox.setMaterial(material);

  }

  public void requestFocus() {
    messageText.requestFocus();

  }

  public TextField getTextField() {
    return messageText;
  }
}
