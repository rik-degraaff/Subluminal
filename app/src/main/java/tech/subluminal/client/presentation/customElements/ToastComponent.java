package tech.subluminal.client.presentation.customElements;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * This component can be used to show a toast message on screen.
 */
public class ToastComponent extends Group {

  public ToastComponent(String msg, AnchorPane parent, Node attach) {
    Label msgLabel = new Label(msg);
    msgLabel.setPadding(new Insets(5));

    this.getChildren().addAll(msgLabel);
    this.getStyleClass().addAll("console-red");



    Platform.runLater(() -> {
      this.layoutXProperty().bind(Bindings.createDoubleBinding(() -> {
        if(parent.getWidth() / 2 < attach.getLayoutX()){
          return attach.getLayoutX() - msgLabel.getWidth();
        }else {
          return attach.getLayoutX();
        }
      }, attach.layoutXProperty(),msgLabel.widthProperty()));

      this.layoutYProperty().bind(Bindings.createDoubleBinding(() -> {
        if(parent.getHeight() / 2 < attach.getLayoutY()){
          return attach.getLayoutY() - msgLabel.getHeight();
        }else {
          return attach.getLayoutY();
        }
      }, attach.layoutYProperty(),msgLabel.heightProperty()));
    });
  }
}
