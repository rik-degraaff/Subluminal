package tech.subluminal.client.presentation.customElements;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.ChatController;
import tech.subluminal.client.presentation.controller.MainController;

/**
 * Represents the chat window.
 */
public class ChatComponent extends AnchorPane {

  private ChatController controller;

  /**
   * Initializes the chat window and attaches it to the a controller.
   *
   * @param mainController the controller to attach to.
   */
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
