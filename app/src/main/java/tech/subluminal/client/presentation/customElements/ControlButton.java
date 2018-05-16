package tech.subluminal.client.presentation.customElements;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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

  public ControlButton(MainController main, String label, Node node, Node parent) {
    this.main = main;
    this.node = node;
    this.parent = parent;

    Label button = new Label(label);

    button.getStyleClass().addAll("button3D");
    button.setTranslateZ(-3);
    button.setBackground(
        new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

    Box box = new Box();

    Platform.runLater(() -> {
      box.widthProperty().bind(((GridPane) this.getParent()).widthProperty());
      box.heightProperty().bind(Bindings.createDoubleBinding(() -> {
        return ((GridPane) this.getParent()).getHeight() / ((GridPane) this.getParent())
            .getChildren().size();
      }, ((GridPane) this.getParent()).widthProperty()));

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

    if (parent instanceof VBox) {

      button.setOnMouseClicked(e -> {

        if (!isOpen) {
          ((VBox) parent).getChildren().add(node);
          button.setText("X");
          isOpen = true;
        } else {
          ((VBox) parent).getChildren().remove(node);
          isOpen = false;
          button.setText(label);
        }
        e.consume();
      });
    } else {
      button.setOnMouseClicked(e -> {
        if (!isOpen) {
          ((DisplayComponent) parent).setDisplay((AnchorPane) node);
          button.setText("X");
          isOpen = true;
        } else {
          ((DisplayComponent) parent).clearDisplay();
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
          new KeyFrame(Duration.seconds(0.1), new KeyValue(trans.zProperty(), 1.5)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scale.zProperty(), 0.5)));
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
