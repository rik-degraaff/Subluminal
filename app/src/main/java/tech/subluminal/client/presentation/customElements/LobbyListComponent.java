package tech.subluminal.client.presentation.customElements;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sun.applet.Main;
import tech.subluminal.client.presentation.controller.LobbyListController;
import tech.subluminal.client.presentation.controller.MainController;

import java.io.IOException;

public class LobbyListComponent extends AnchorPane {
    public LobbyListComponent(MainController mainController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LobbyListView.fxml"));
        fxmlLoader.setRoot(this);
        //fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            LobbyListController controller = fxmlLoader.getController();
            controller.setMainController(mainController);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
