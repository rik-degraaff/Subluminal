package tech.subluminal.client.presentation.customElements;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.LobbyListController;

public class LobbyListComponent extends AnchorPane {

  private LobbyListController controller;

  public LobbyListComponent() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LobbyListView.fxml"));
    fxmlLoader.setRoot(this);
    //fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      controller = fxmlLoader.getController();
      //controller.setLobbyStore(store);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  public LobbyListController getController() {
    return controller;
  }

  public void setController(LobbyListController controller) {
    this.controller = controller;
  }
}
