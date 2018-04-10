package tech.subluminal.client.presentation.customElements;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.presentation.controller.MenuController;

import java.io.IOException;

public class MenuComponent extends AnchorPane {

  public MenuComponent(MainController mainController) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
    fxmlLoader.setRoot(this);
    //fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      MenuController controller = fxmlLoader.getController();
      controller.setMainController(mainController);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
