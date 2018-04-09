package tech.subluminal.client.presentation.customElements;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import tech.subluminal.shared.records.PlayerStatus;


/**
 * Custom Element to define the PlayerStatusComponent Representation for each Player.
 */
public class PlayerStatusComponent extends HBox {

    Rectangle statusBox;

    /**
     * Custom Compenent to render the player status.
     *
     * @param username is the name of the player.
     * @param status   is the active status of the player.
     */
    public PlayerStatusComponent(String username, PlayerStatus status) {
        HBox hbox = new HBox();

        statusBox = new Rectangle();
        statusBox.setFill(status.getColor());
        Label name = new Label(username);

        hbox.getChildren().add(statusBox);
        hbox.setSpacing(5);
        hbox.getChildren().add(name);
        this.getChildren().add(hbox);
        Platform.runLater(() -> {
            statusBox.setHeight(this.getHeight());
            statusBox.setWidth(20);
        });

    }

    /**
     * Updates the status of a Player on request.
     *
     * @param status
     */
    public void updateStatus(PlayerStatus status) {
        statusBox.setFill(status.getColor());
    }

}

