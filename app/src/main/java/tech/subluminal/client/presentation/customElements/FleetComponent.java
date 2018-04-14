package tech.subluminal.client.presentation.customElements;

import java.awt.TextField;
import java.util.List;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class FleetComponent extends Group {
  private final ObjectProperty<Color> color = new SimpleObjectProperty<Color>();

  private final BooleanProperty isRotating = new SimpleBooleanProperty();
  private final int fromCenter = 100;
  private final StringProperty ownerID = new SimpleStringProperty();
  private final StringProperty fleetID = new SimpleStringProperty();
  private final IntegerProperty numberOfShips = new SimpleIntegerProperty();

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

  public String getFleetID() {
    return fleetID.get();
  }

  public StringProperty fleetIDProperty() {
    return fleetID;
  }

  public void setFleetID(String fleetID) {
    this.fleetID.set(fleetID);
  }

  public int getNumberOfShips() {
    return numberOfShips.get();
  }

  public IntegerProperty numberOfShipsProperty() {
    return numberOfShips;
  }

  public void setNumberOfShips(int numberOfShips) {
    this.numberOfShips.set(numberOfShips);
  }

  public FleetComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID){
    Group group = new Group();
    group.getTransforms().add(new Translate(-fromCenter, -fromCenter));
    group.getTransforms().add(new Rotate(90));

    Platform.runLater(() -> {
      this.setLayoutX(coordinates.getX()* getScene().getWidth());
      this.setLayoutY(coordinates.getY()* getScene().getHeight());
    });

    this.setOwnerID(ownerID);
    this.setFleetID(ID);
    this.setNumberOfShips(numberOfShips);


    Polygon ship = new Polygon();
    ship.getPoints().addAll(new Double[]{
        0.0, 0.0,
        -10.0, 20.0,
        10.0, 20.0 });
    ship.setFill(Color.PINK);
    Label amount = new Label();

    amount.textProperty().bind(Bindings.createStringBinding(() ->
      this.numberOfShipsProperty().getValue().toString(), numberOfShipsProperty()));

    group.getChildren().add(ship);
    group.getChildren().add(amount);

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
