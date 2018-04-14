package tech.subluminal.client.presentation.customElements;

import javafx.animation.RotateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Fleet extends Group {
  private final ObjectProperty<Color> color = new SimpleObjectProperty<Color>();

  private final BooleanProperty isRotating = new SimpleBooleanProperty();
  private final int fromCenter = 100;
  private final StringProperty ownerID = new SimpleStringProperty();

  public Color getColor() {
    return color.get();
  }

  public ObjectProperty<Color> colorProperty() {
    return color;
  }

  public void setColor(Color color) {
    this.color.set(color);
  }

  public boolean isIsRotating() {
    return isRotating.get();
  }

  public BooleanProperty isRotatingProperty() {
    return isRotating;
  }

  public void setIsRotating(boolean isRotating) {
    this.isRotating.set(isRotating);
  }

  public String getOwnerID() {
    return ownerID.get();
  }

  public StringProperty ownerIDProperty() {
    return ownerID;
  }

  public void setOwnerID(String ownerID) {
    this.ownerID.set(ownerID);
  }

  public Fleet(double x, double y, int amount, String playerId){
    Group group = new Group();
    group.getTransforms().add(new Translate(-fromCenter, -fromCenter));
    group.getTransforms().add(new Rotate(90));

    this.setLayoutX(x);
    this.setLayoutY(y);
    this.setOwnerID(playerId);

    Polygon ship = new Polygon();
    ship.getPoints().addAll(new Double[]{
        -20.0, -20.0,
        20.0, 0.0,
        0.0, 20.0 });
    ship.setFill(Color.PINK);

    group.getChildren().add(ship);

    RotateTransition rotateTl = new RotateTransition(Duration.seconds(5), group);
    rotateTl.setToAngle(360);
    rotateTl.setCycleCount(RotateTransition.INDEFINITE);

    isRotatingProperty().addListener((observable, oldValue, newValue) -> {
      if(newValue == true){
        rotateTl.play();
      }else{
        rotateTl.stop();
      }
    });


    this.getChildren().add(group);
  }

}
