package tech.subluminal.client.presentation.customElements;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tech.subluminal.client.presentation.controller.MainController;
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
   * @param status is the active status of the player.
   */
  public PlayerStatusComponent(String username, PlayerStatus status, MainController main, boolean showMsg) {
    HBox hbox = new HBox();

    statusBox = new Rectangle();
    statusBox.setFill(status.getColor());
    Label name = new Label(username);
    name.setTextFill(Color.WHITE);

    hbox.getChildren().add(statusBox);
    hbox.setPadding(new Insets(0,0,0,20));
    hbox.setSpacing(20);
    hbox.getChildren().add(name);
    if(showMsg){
      Label msg = new Label("msg");
      msg.setOnMouseClicked(e -> main.sendRecipiantToChat(username));
      hbox.getChildren().add(msg);
      msg.setTextFill(Color.WHITE);
    }
    this.getChildren().add(hbox);
    this.getStyleClass().add("player-status");

    statusBox.setHeight(20);
    statusBox.setWidth(20);

  }

  /**
   * Updates the status of a Player on request.
   */
  public void updateStatus(PlayerStatus status) {
    statusBox.setFill(status.getColor());
  }

}

