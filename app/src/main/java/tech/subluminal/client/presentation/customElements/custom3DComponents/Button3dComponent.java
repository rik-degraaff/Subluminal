package tech.subluminal.client.presentation.customElements.custom3DComponents;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Button3dComponent extends AnchorPane {
  private final StringProperty text = new SimpleStringProperty();


  public Button3dComponent(String text){
    textProperty().set(text);
    Label label = new Label();
    label.textProperty().bind(textProperty());
    label.prefHeightProperty().bind(this.heightProperty().subtract(10));
    label.prefWidthProperty().bind(this.widthProperty().subtract(10));

    label.getStyleClass().addAll("button3D", "font-dos");
    label.setTranslateZ(-3);

    Box box = new Box();

    box.widthProperty().bind(widthProperty());
    box.heightProperty().bind(this.heightProperty());
    box.translateXProperty().bind(Bindings.createDoubleBinding(() -> getWidth()/2 + 5, widthProperty()));
    box.translateYProperty().bind(Bindings.createDoubleBinding(() -> getHeight()/2 + 5, heightProperty()));


    box.setDepth(10);

    PhongMaterial material = new PhongMaterial();
    material.setSpecularColor(Color.BLACK);
    material.setDiffuseColor(Color.GREY);
    box.setMaterial(material);

    this.getChildren().addAll(box, label);

    Translate trans = new Translate();
    label.getTransforms().add(trans);

    Scale scale = new Scale();
    box.getTransforms().add(scale);

    label.setOnMouseEntered((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(trans.zProperty(), 5)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scale.zProperty(), 0.2)));
      timeTl.play();
    });
    label.setOnMouseExited((e) -> {
      Timeline timeTl = new Timeline();
      timeTl.getKeyFrames().addAll(
          new KeyFrame(Duration.seconds(0.1), new KeyValue(trans.zProperty(), 0)),
          new KeyFrame(Duration.seconds(0.1), new KeyValue(scale.zProperty(), 1)));
      timeTl.play();
    });
  }

  public String getText() {
    return text.get();
  }

  public StringProperty textProperty() {
    return text;
  }

  public void setText(String text) {
    this.text.set(text);
  }
}
