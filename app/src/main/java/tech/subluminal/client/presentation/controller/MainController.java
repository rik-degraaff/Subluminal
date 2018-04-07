package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.customElements.BackgroundComponent;
import tech.subluminal.client.presentation.customElements.MenuComponent;

public class MainController implements Initializable{

  @FXML
  private Parent chatView;
  @FXML
  private ChatController chatViewController;

  @FXML
  private Parent userListView;
  @FXML
  private UserListController userListController;

  @FXML
  private AnchorPane spaceBackgroundDock;

  @FXML
  private AnchorPane chatDock;

  @FXML
  private AnchorPane menuDock;

  private BackgroundComponent background;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    background = new BackgroundComponent(2000);
    spaceBackgroundDock.getChildren().add(background);
    menuDock.getChildren().add(new MenuComponent());
  }


  public MainController getController(){
    return this;
  }

  public ChatController getChatController() {
    return this.chatViewController;
  }

  public void onWindowResizeHandle(int diffX, int diffY) {
    background.onWindowResize(diffX,diffY);
  }

  public void onJoinPressed() {
    System.out.println("here");
  }
}
