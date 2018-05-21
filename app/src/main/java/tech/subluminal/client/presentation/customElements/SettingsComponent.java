package tech.subluminal.client.presentation.customElements;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.KeyMap;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.presentation.controller.SettingsController;

public class SettingsComponent extends AnchorPane {

  public SettingsComponent(MainController mainController,
      KeyMap keyMap) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingsView.fxml"));
    fxmlLoader.setRoot(this);
    //fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      SettingsController controller = fxmlLoader.getController();
      controller.setMainController(mainController);
      controller.setKeyMap(keyMap);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
