package tech.subluminal.client.presentation.customElements;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import tech.subluminal.client.presentation.controller.MainController;

public class ControlButton extends Group {

  private boolean isOpen;
  Node parent;
  Node node;
  MainController main;

  public ControlButton(MainController main, String label, Node node, Node parent) {
    this.main = main;
    this.node = node;
    this.parent = parent;
    this.prefWidth(40);
    this.prefHeight(40);

    Button button = new Button();
    button.setText(label);
    button.getStyleClass().addAll("button3D");
    button.setTranslateZ(-3);

    Box box = new Box();
    box.setWidth(20);
    box.setHeight(20);
    box.setDepth(5);
    box.setTranslateX(12.5);
    box.setTranslateY(12.5);

    PhongMaterial material = new PhongMaterial();
    material.setSpecularColor(Color.BLACK);
    material.setDiffuseColor(Color.GREY);
    box.setMaterial(material);

    this.getChildren().addAll(box, button);

    if (parent instanceof VBox) {

      button.setOnAction(e -> {

        if (!isOpen) {
          ((VBox)parent).getChildren().add(node);
          button.setText("X");
          isOpen = true;
        } else {
          ((VBox) parent).getChildren().remove(node);
          isOpen = false;
          button.setText(label);
        }
      });
    }else {
      button.setOnAction(e -> {
        if (!isOpen) {
          ((DisplayComponent)parent).setDisplay((AnchorPane)node);
          button.setText("X");
          isOpen = true;
        } else {
          ((DisplayComponent)parent).clearDisplay();
          isOpen = false;
          button.setText(label);
        }
      });
    }

    Translate trans = new Translate();
    button.getTransforms().add(trans);

    Scale scale = new Scale();
    box.getTransforms().add(scale);

    button.setOnMouseEntered((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(trans.zProperty(),1.5)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scale.zProperty(), 0.5)));
      timeTl.play();
    });
    button.setOnMouseExited((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(trans.zProperty(),0)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scale.zProperty(), 1)));
      timeTl.play();
    });
  }
}
