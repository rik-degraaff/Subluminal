package tech.subluminal.client.presentation.customElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

/**
 * This component can be used to show a toast message on screen.
 */
public class ToastComponent extends AnchorPane {

  private boolean permanent;

  public ToastComponent(String msg, boolean permanent) {
    this.permanent = permanent;
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

  public boolean isPermanent() {
    return permanent;
  }

  public void setPermanent(boolean permanent) {
    this.permanent = permanent;
  }
}
