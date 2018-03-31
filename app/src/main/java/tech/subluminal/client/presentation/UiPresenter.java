package tech.subluminal.client.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tech.subluminal.client.presentation.controller.FXMLController;

public class UiPresenter extends Application implements ChatPresenter, UserPresenter {

  volatile boolean keepRunning = true;
  private ChatPresenter.Delegate chatDelegate;
  private UserPresenter.Delegate userDelegate;
  private FXMLController controller;


  /**
   * Fired when a someone sends a message to all users on the server.
   *
   * @param message is the text of the message.
   * @param username from the sender of the message.
   */
  @Override
  public void globalMessageReceived(String message, String username) {
    FXMLController controller = new FXMLController();
    controller.addMessageChat(message, username);
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
    this.chatDelegate = delegate;
  }

  /**
   * Function that should be called when login succeeded.
   */
  @Override
  public void loginSucceeded() {
    //TODO: complete this
    controller.addMessageChat("Successfully logged in as " + ".");
  }

  /**
   * Gets called when the client got logged out.
   */
  @Override
  public void logoutSucceeded() {
    controller.addMessageChat("Successfully logged out"); //TODO: add username
  }

  /**
   * Fired when a username got successfully changed.
   */
  @Override
  public void nameChangeSucceeded() {

  }

  @Override
  public void setUserDelegate(UserPresenter.Delegate delegate) {
    this.userDelegate = delegate;
  }

  @Override
  public void start(Stage primaryStage) throws Exception{
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("view/LobbyView.fxml"));
    Parent root = loader.load();
    root.getStylesheets().add(getClass().getResource("view/sample.css").toExternalForm());

    primaryStage.setTitle("Subluminal Lobby");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    controller = loader.getController();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
