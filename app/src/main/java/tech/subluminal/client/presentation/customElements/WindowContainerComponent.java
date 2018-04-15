package tech.subluminal.client.presentation.customElements;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.presentation.controller.WindowContainerController;

public class WindowContainerComponent extends AnchorPane {


  private WindowContainerController controller;


  public WindowContainerComponent(MainController mainController, Node node, String titel) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowContainerView.fxml"));
    fxmlLoader.setRoot(this);
    //fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      controller = fxmlLoader.getController();
      controller.setMainController(mainController);
      controller.setChild(node);
      //controller.setLobbyStore(store);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    controller.setTitel(titel);
  }

  public void onWindowOpen() {
    controller.openWindow();
  }

  public void onWindowClose() {
    controller.closeWindow();
  }
}
