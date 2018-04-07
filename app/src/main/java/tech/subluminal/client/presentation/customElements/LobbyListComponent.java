package tech.subluminal.client.presentation.customElements;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.LobbyListController;

import java.io.IOException;

public class LobbyListComponent extends AnchorPane {
    public LobbyListComponent(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LobbyListView.fxml"));
        fxmlLoader.setRoot(this);
        //fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
