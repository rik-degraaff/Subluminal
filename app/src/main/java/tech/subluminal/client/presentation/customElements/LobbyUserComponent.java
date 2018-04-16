package tech.subluminal.client.presentation.customElements;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.LobbyUserController;
import tech.subluminal.client.presentation.controller.MainController;

public class LobbyUserComponent extends AnchorPane {

  private LobbyUserController controller;

  public LobbyUserComponent(MainController mainController) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LobbyUserView.fxml"));
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
