package tech.subluminal.client.presentation.customElements;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.LobbyListController;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.stores.LobbyStore;

import java.io.IOException;

public class LobbyListComponent extends AnchorPane {

    private LobbyListController controller;

    public LobbyListComponent(MainController mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LobbyListView.fxml"));
        fxmlLoader.setRoot(this);
        //fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setMainController(mainController);
            //controller.setLobbyStore(store);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onWindowOpen(){
        controller.openWindow();
    }
}
