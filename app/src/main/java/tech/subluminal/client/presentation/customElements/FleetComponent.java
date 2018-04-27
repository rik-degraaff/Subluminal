package tech.subluminal.client.presentation.customElements;

import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class FleetComponent extends Group {

  private final ObjectProperty<Color> color = new SimpleObjectProperty<Color>();

  private final BooleanProperty isRotating = new SimpleBooleanProperty();
  private final int fromCenter = 100;
  private final StringProperty ownerID = new SimpleStringProperty();
  private final StringProperty fleetID = new SimpleStringProperty();
  private final IntegerProperty numberOfShips = new SimpleIntegerProperty();
  private final ObservableList<String> targetIDs = FXCollections.observableArrayList();
  private final ListProperty<String> targetsWrapper = new SimpleListProperty<>(targetIDs);

  private final DoubleProperty x = new SimpleDoubleProperty();
  private final DoubleProperty y = new SimpleDoubleProperty();

  private final IntegerProperty parentWidthProperty = new SimpleIntegerProperty();
  private final IntegerProperty parentHeightProperty = new SimpleIntegerProperty();
  private final RotateTransition rotateTl;

  public FleetComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID,
      List<String> targetIDs) {
    Group group = new Group();
    group.getTransforms().add(new Translate(-fromCenter, -fromCenter));
    group.getTransforms().add(new Rotate(90));

    setX(coordinates.getX());
    setY(coordinates.getY());

    Platform.runLater(() -> {
      this.parentWidthProperty.bind(getScene().widthProperty());
      this.parentHeightProperty.bind(getScene().heightProperty());

      this.layoutXProperty().bind(Bindings
          .createDoubleBinding(() -> parentWidthProperty.doubleValue() / 2 + (getX() - 0.5) * Math
                  .min(parentWidthProperty.doubleValue(), parentHeightProperty.doubleValue()),
              xProperty(), parentWidthProperty, parentHeightProperty));
      this.layoutYProperty().bind(Bindings
          .createDoubleBinding(() -> parentHeightProperty.doubleValue() / 2 + (getY() - 0.5) * Math
                  .min(parentWidthProperty.doubleValue(), parentHeightProperty.doubleValue()),
              yProperty(), parentWidthProperty, parentHeightProperty));
    });


    this.setOwnerID(ownerID);

    setTargetsWrapper(targetIDs);

    this.setOwnerID(ownerID);
    this.setFleetID(ID);
    this.setNumberOfShips(numberOfShips);

    Polygon ship = new Polygon();
    ship.getPoints().addAll(new Double[]{
        0.0, 0.0,
        -10.0, 20.0,
        10.0, 20.0});
    ship.fillProperty().bind(colorProperty());
    
    group.setMouseTransparent(true);

    rotateTl = new RotateTransition(Duration.seconds(5), group);
    rotateTl.setToAngle(360);
    rotateTl.setCycleCount(RotateTransition.INDEFINITE);
    rotateTl.setInterpolator(Interpolator.LINEAR);
    Label amount = new Label();

    amount.textProperty().bind(Bindings.createStringBinding(() ->
        this.numberOfShipsProperty().getValue().toString(), numberOfShipsProperty()));

    group.getChildren().add(ship);
    group.getChildren().add(amount);

    RotateTransition rotateTl = new RotateTransition(Duration.seconds(5), group);
    rotateTl.setToAngle(360);
    rotateTl.setCycleCount(RotateTransition.INDEFINITE);

    Platform.runLater(() -> {
      targetsWrapperProperty().addListener((observable, oldValue, newValue) -> {
        Logger.debug("FLEET GOT: " + targetsWrapperProperty().toString());
        if (targetsWrapperProperty().isEmpty() && !isIsRotating()) {
          setIsRotating(true);
        } else if (!targetsWrapperProperty().isEmpty() && isIsRotating()) {
          setIsRotating(false);
        }
      });

      isRotatingProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && !oldValue) {
          rotateTl.play();
        } else if (!newValue && oldValue) {
          rotateTl.pause();
        }
      });
    });


    isRotatingProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == true && oldValue == false) {
        rotateTl.play();
      } else if (newValue == false && oldValue == true) {
        rotateTl.stop();
      }
    });

    this.getChildren().add(group);
  }


  public void setTargetIDs(List<String> targetIDs) {
    targetIDs.forEach(i -> this.targetIDs.add(i));
  }

  public ObservableList<String> getTargetIDs() {
    return targetIDs;
  }

  public ListProperty<String> targetsWrapperProperty() {
    return targetsWrapper;
  }

  public ObservableList<String> getTargetsWrapper() {
    return targetsWrapper.get();
  }

  public void setTargetsWrapper(ObservableList<String> targetsWrapper) {
    this.targetsWrapper.set(targetsWrapper);
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


  public double getX() {
    return x.get();
  }

  public DoubleProperty xProperty() {
    return x;
  }

  public void setX(double x) {
    this.x.set(x);
  }

  public double getY() {
    return y.get();
  }

  public DoubleProperty yProperty() {
    return y;
  }

  public void setY(double y) {
    this.y.set(y);
  }

  public int getParentWidthProperty() {
    return parentWidthProperty.get();
  }

  public IntegerProperty parentWidthPropertyProperty() {
    return parentWidthProperty;
  }

  public void setParentWidthProperty(int parentWidthProperty) {
    this.parentWidthProperty.set(parentWidthProperty);
  }

  public int getParentHeightProperty() {
    return parentHeightProperty.get();
  }

  public IntegerProperty parentHeightPropertyProperty() {
    return parentHeightProperty;
  }

  public void setParentHeightProperty(int parentHeightProperty) {
    this.parentHeightProperty.set(parentHeightProperty);
  }

  public void setTargetsWrapper(List<String> targetIDs) {
    this.targetIDs.setAll(targetIDs);
  }
}
