package tech.subluminal.client.presentation.customElements;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.LobbyUserController;

public class LobbyUserComponent extends AnchorPane {

  private LobbyUserController controller;

  public LobbyUserComponent() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LobbyUserView.fxml"));
    fxmlLoader.setRoot(this);
    //fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      controller = fxmlLoader.getController();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  public LobbyUserController getController() {
    return controller;
  }

  public void setController(LobbyUserController controller) {
    this.controller = controller;
  }
}
