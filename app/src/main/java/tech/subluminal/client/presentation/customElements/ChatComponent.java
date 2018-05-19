package tech.subluminal.client.presentation.customElements;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.ChatController;
import tech.subluminal.client.presentation.controller.MainController;

public class ChatComponent extends AnchorPane {

  private ChatController controller;

  public ChatComponent(MainController mainController) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChatView.fxml"));
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

  public ChatController getChatcontroller() {
    return controller;
  }

  public TextField getTextField() {
    return controller.getTextField();
  }
}
