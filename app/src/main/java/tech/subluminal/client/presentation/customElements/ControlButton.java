package tech.subluminal.client.presentation.customElements;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.presentation.customElements.custom3DComponents.Button3dComponent;

public class ControlButton extends AnchorPane {

  Node parent;
  Node node;
  MainController main;
  private boolean isOpen;

  public ControlButton(MainController main, String label, Node node, Node goal) {
    this.main = main;
    this.node = node;
    this.parent = goal;

    Button3dComponent button = new Button3dComponent(label);

    button.getStyleClass().addAll("button3D", "font-dos");

    button.prefWidthProperty().bind(this.widthProperty());
    button.prefHeightProperty().bind(this.heightProperty());


    this.getChildren().addAll(button);

    if (goal instanceof VBox) {

      button.setOnMouseClicked(e -> {

        if (!isOpen) {
          ((VBox) goal).getChildren().add(node);
          button.setText("X");
          isOpen = true;
        } else {
          ((VBox) goal).getChildren().remove(node);
          isOpen = false;
          button.setText(label);
        }
        e.consume();
      });
    } else {
      button.setOnMouseClicked(e -> {
        if (!isOpen) {
          ((DisplayComponent) goal).clearDisplay();
          ((DisplayComponent) goal).setDisplay((AnchorPane) node);
          button.setText("X");
          isOpen = true;
        } else {
          ((DisplayComponent) goal).clearDisplay();
          isOpen = false;
          button.setText(label);
        }
        e.consume();
      });
    }

  }
}
