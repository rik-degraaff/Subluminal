package tech.subluminal.client.presentation.customElements;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.ChatController;

import java.io.IOException;

public class ChatComponent extends AnchorPane {

    private ChatController controller;

    public ChatComponent(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "tech.subluminal.client.presentation.view.ChatView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ChatController getController(){
        return controller;
    }
}
