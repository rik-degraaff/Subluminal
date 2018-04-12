package tech.subluminal.client.presentation.customElements;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.GameController;
import tech.subluminal.client.presentation.controller.MainController;

public class GameComponent extends AnchorPane {

  GameController controller;

  public GameComponent(MainController mainController){
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameView.fxml"));
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
