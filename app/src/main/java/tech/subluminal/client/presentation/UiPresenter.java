package tech.subluminal.client.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tech.subluminal.client.presentation.controller.LobbyController;

public class UiPresenter extends Application implements ChatPresenter, UserPresenter{

  public static LobbyController controller;
  public FXMLLoader loader;
  private ChatPresenter.Delegate chatDelegate;
  private UserPresenter.Delegate userDelegate;

  public UiPresenter(){
    //launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception{
    loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("view/LobbyView.fxml"));
    //loader.setController(controller);
    Parent root = loader.load();
    root.getStylesheets().add(getClass().getResource("view/sample.css").toExternalForm());

    controller = loader.getController();

    primaryStage.setTitle("Subluminal Lobby");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

  }


  public static void main(String[] args) {
    launch(args);
  }

  public LobbyController getController(){
    return this.getController();
  }

  @Override
  public void globalMessageReceived(String message, String username) {
    //controller.addMessageChat(message, username);
  }

  @Override
  public void whisperMessageReceived(String message, String username) {

  }

  @Override
  public void gameMessageReceived(String message, String username) {

  }

  @Override
  public void setChatDelegate(ChatPresenter.Delegate delegate) {

  }

  @Override
  public void loginSucceeded() {
    //controller.addMessageChat("Successfully logged in");
    System.out.println("test");
  }

  @Override
  public void logoutSucceeded() {

  }

  @Override
  public void nameChangeSucceeded() {

  }

  @Override
  public void setUserDelegate(UserPresenter.Delegate delegate) {

  }
}
