package tech.subluminal.client.presentation.customElements;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import tech.subluminal.shared.records.Status;

/**
 * Custom Element to define the Status Representation for each Player.
 */
public class PlayerStatus extends HBox {
    public PlayerStatus(String username, Status status){
        HBox hbox = new HBox();

        Rectangle statusBox = new Rectangle(5,5);
        Label name = new Label(username);

        hbox.getChildren().add(statusBox);
        hbox.getChildren().add(name);
    }
}
