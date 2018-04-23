package tech.subluminal.client.presentation.customElements;

import java.util.List;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
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
  private final ListProperty<String> targetIDs = new SimpleListProperty<String>();

  public FleetComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID,
      List<String> targetIDs) {
    Group group = new Group();
    group.getTransforms().add(new Translate(-fromCenter, -fromCenter));
    group.getTransforms().add(new Rotate(90));

    Platform.runLater(() -> {
      this.setLayoutX(coordinates.getX() * getScene().getWidth());
      this.setLayoutY(coordinates.getY() * getScene().getHeight());
    });

    this.setTargetIDs(targetIDs);

    targetIDsProperty().addListener((observable, oldValue, newValue) -> {
      if (!oldValue.isEmpty() && newValue.isEmpty()) {
        setIsRotating(true);
      } else if (oldValue.isEmpty() && !newValue.isEmpty()) {
        setIsRotating(false);
      }
    });

    this.setOwnerID(ownerID);
    this.setFleetID(ID);
    this.setNumberOfShips(numberOfShips);

    Polygon ship = new Polygon();
    ship.getPoints().addAll(new Double[]{
        0.0, 0.0,
        -10.0, 20.0,
        10.0, 20.0});
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
      if (newValue == true && oldValue == false) {
        rotateTl.play();
      } else if (newValue == false && oldValue == true) {
        rotateTl.stop();
      }
    });

    this.getChildren().add(group);
  }

  public ObservableList<String> getTargetIDs() {
    return targetIDs.get();
  }

  public void setTargetIDs(List<String> targetIDs) {
    targetIDs.forEach(i -> this.targetIDs.add(i));
  }

  public ListProperty<String> targetIDsProperty() {
    return targetIDs;
  }

  public Color getColor() {
    return color.get();
  }

  public void setColor(Color color) {
    this.color.set(color);
  }

  public ObjectProperty<Color> colorProperty() {
    return color;
  }

  public boolean isIsRotating() {
    return isRotating.get();
  }

  public void setIsRotating(boolean isRotating) {
    this.isRotating.set(isRotating);
  }

  public BooleanProperty isRotatingProperty() {
    return isRotating;
  }

  public String getOwnerID() {
    return ownerID.get();
  }

  public void setOwnerID(String ownerID) {
    this.ownerID.set(ownerID);
  }

  public StringProperty ownerIDProperty() {
    return ownerID;
  }

  public String getFleetID() {
    return fleetID.get();
  }

  public void setFleetID(String fleetID) {
    this.fleetID.set(fleetID);
  }

  public StringProperty fleetIDProperty() {
    return fleetID;
  }

  public int getNumberOfShips() {
    return numberOfShips.get();
  }

  public void setNumberOfShips(int numberOfShips) {
    this.numberOfShips.set(numberOfShips);
  }

  public IntegerProperty numberOfShipsProperty() {
    return numberOfShips;
  }

}
