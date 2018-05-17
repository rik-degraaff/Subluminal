package tech.subluminal.client.presentation.customElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 * This component can be used to show a toast message on screen.
 */
public class ToastComponent extends Group {

  public ToastComponent(String msg) {
    Label msgLabel = new Label(msg);
    msgLabel.setPadding(new Insets(5));
    msgLabel.setMaxWidth(500);

    this.getChildren().addAll(msgLabel);
    msgLabel.getStyleClass().addAll("console-red");
    msgLabel.setAlignment(Pos.CENTER);
    msgLabel.setTextAlignment(TextAlignment.CENTER);
    msgLabel.setWrapText(true);

    this.maxWidth(500);

  }
}
