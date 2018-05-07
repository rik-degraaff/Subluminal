package tech.subluminal.client.presentation.customElements;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.presentation.controller.UserListController;


public class UserListComponent extends AnchorPane {

  UserListController controller;

  public UserListController getController() {
    return controller;
  }

  public void setController(UserListController controller) {
    this.controller = controller;
  }

  public UserListComponent(MainController mainController) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserListView.fxml"));
    fxmlLoader.setRoot(this);
    //fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      controller = fxmlLoader.getController();
      controller.setMainController(mainController);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
