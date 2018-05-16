package tech.subluminal.client.presentation.customElements;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import tech.subluminal.client.presentation.controller.MainController;

public class ControlButton extends Group {

  Node parent;
  Node node;
  MainController main;
  private boolean isOpen;

  public ControlButton(MainController main, String label, Node node, Node goal, GridPane parent) {
    this.main = main;
    this.node = node;
    this.parent = goal;

    Label button = new Label(label);

    button.getStyleClass().addAll("button3D", "font-dos");
    button.setTranslateZ(-3);

    Box box = new Box();

    Platform.runLater(() -> {
      box.widthProperty().bind(parent.widthProperty());
      box.heightProperty().bind(Bindings.createDoubleBinding(() -> {
        return parent.getHeight() / parent
            .getChildren().size() - 5;
      }, parent.heightProperty(), parent.getChildren()));

      button.prefWidthProperty().bind(box.widthProperty());
      button.prefHeightProperty().bind(box.heightProperty());

      button.translateXProperty()
          .bind(Bindings.createDoubleBinding(() -> -box.getWidth() / 2, box.widthProperty()));
      button.translateYProperty()
          .bind(Bindings.createDoubleBinding(() -> -box.getHeight() / 2, box.heightProperty()));
    });

    box.setDepth(5);

    PhongMaterial material = new PhongMaterial();
    material.setSpecularColor(Color.BLACK);
    material.setDiffuseColor(Color.GREY);
    box.setMaterial(material);

    this.getChildren().addAll(box, button);

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

    Translate trans = new Translate();
    button.getTransforms().add(trans);

    Scale scale = new Scale();
    box.getTransforms().add(scale);

    button.setOnMouseEntered((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(trans.zProperty(), 2)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scale.zProperty(), 0.2)));
      timeTl.play();
    });
    button.setOnMouseExited((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(trans.zProperty(), 0)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scale.zProperty(), 1)));
      timeTl.play();
    });
  }
}
