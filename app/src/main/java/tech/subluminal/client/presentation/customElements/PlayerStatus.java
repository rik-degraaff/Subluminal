package tech.subluminal.client.presentation.customElements;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tech.subluminal.shared.records.Status;


/**
 * Custom Element to define the Status Representation for each Player.
 */
public class PlayerStatus extends HBox {

    Rectangle statusBox;

    /**
     * Custom Compenent to render the player status.
     * @param username is the name of the player.
     * @param status is the active status of the player.
     */
    public PlayerStatus(String username, Status status){
        HBox hbox = new HBox();

        statusBox = new Rectangle();
        statusBox.setFill(getStatusColor(status));
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
     * @param status
     */
    public void updateStatus(Status status){
        statusBox.setFill(getStatusColor(status));
    }

    /**
     * Returns the corresponding color to desplay for a player.
     * @param status is the active status of the player.
     * @return the color which represents the status of the player.
     */
    private Color getStatusColor(Status status) {
        Color color;

        switch (status){
            case INGAME:
                color = Color.RED;
                break;
            case ONLINE:
                color = Color.GREEN;
                break;
            case INLOBBY:
                color = Color.YELLOW;
                break;
            default:
                color = Color.GRAY;
        }
        return color;
    }
}

