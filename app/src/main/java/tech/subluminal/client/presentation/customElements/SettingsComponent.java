package tech.subluminal.client.presentation.customElements;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.presentation.controller.SettingsController;

import java.io.IOException;

public class SettingsComponent extends AnchorPane {
    public SettingsComponent(MainController mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingsView.fxml"));
        fxmlLoader.setRoot(this);
        //fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            SettingsController controller = fxmlLoader.getController();
            controller.setMainController(mainController);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
