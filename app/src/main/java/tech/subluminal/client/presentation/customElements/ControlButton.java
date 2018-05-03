package tech.subluminal.client.presentation.customElements;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tech.subluminal.client.presentation.controller.MainController;

public class ControlButton extends Button {

  private boolean isOpen;
  Node parent;
  Node node;
  MainController main;

  public ControlButton(MainController main, String label, Node node, Node parent) {
    this.main = main;
    this.node = node;
    this.parent = parent;
    this.setText(label);

    if (parent instanceof VBox) {

      this.setOnAction(e -> {

        if (!isOpen) {
          ((VBox)parent).getChildren().add(node);
          this.setText("X");
          isOpen = true;
        } else {
          ((VBox) parent).getChildren().remove(node);
          isOpen = false;
          this.setText(label);
        }
      });
    }
  }
}
