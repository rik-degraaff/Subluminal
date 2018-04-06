package tech.subluminal.client.presentation.customElements;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MenuComponent extends AnchorPane {

        public MenuComponent() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
            fxmlLoader.setRoot(this);
            //fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
}
