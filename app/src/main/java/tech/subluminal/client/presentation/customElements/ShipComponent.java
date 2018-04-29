package tech.subluminal.client.presentation.customElements;

import java.util.List;
import java.util.function.Function;
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
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Star;

public abstract class ShipComponent extends Group {

  private final ObjectProperty<Color> color = new SimpleObjectProperty<>();

  private final BooleanProperty isRotating = new SimpleBooleanProperty();
  private final int fromCenter = 10;
  private final int fromCenterFleet = 30;
  private final StringProperty ownerID = new SimpleStringProperty();
  private final ObservableList<String> targetIDs = FXCollections.observableArrayList();
  private final ListProperty<String> targetsWrapper = new SimpleListProperty<>(targetIDs);

  private final DoubleProperty x = new SimpleDoubleProperty();
  private final DoubleProperty y = new SimpleDoubleProperty();

  private final IntegerProperty parentWidthProperty = new SimpleIntegerProperty();
  private final IntegerProperty parentHeightProperty = new SimpleIntegerProperty();
  private final IntegerProperty numberOfShips = new SimpleIntegerProperty();
  private final RotateTransition rotateTl;

  private GameStore gamestore;
  private boolean isMoving = false;
  public Group group;

  public ShipComponent(Coordinates coordinates, String playerId, List<String> targetIDs) {
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

    this.setOwnerID(playerId);

    setTargetsWrapper(targetIDs);

    setColor(Color.GRAY);
    Polygon ship = new Polygon();
    ship.getPoints().addAll(new Double[]{
        -10.0, -10.0,
        10.0, 0.0,
        0.0, 10.0});
    ship.fillProperty().bind(colorProperty());

    group.getChildren().add(ship);
    group.setMouseTransparent(true);

    rotateTl = new RotateTransition(Duration.seconds(5), group);
    rotateTl.setToAngle(360);
    rotateTl.setCycleCount(RotateTransition.INDEFINITE);
    rotateTl.setInterpolator(Interpolator.LINEAR);

    Platform.runLater(() -> {
      targetsWrapperProperty().addListener((observable, oldValue, newValue) -> {
        //Logger.debug("SHIP GOT: " + targetsWrapperProperty().toString());
        Logger.debug("SOMETHING CHANGED " + oldValue + " " + newValue);
        if (targetsWrapperProperty().isEmpty() && !isIsRotating()) {
          setIsRotating(true);
        } else if (!targetsWrapperProperty().isEmpty() && isIsRotating()) {
          setIsRotating(false);

        } else {
          rotateToStar(group);
        }
      });

      isRotatingProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && !oldValue) {
          group.getTransforms().add(new Translate(-fromCenter, -fromCenter));
          group.getTransforms().add(new Rotate(90));
          rotateTl.play();
        } else if (!newValue && oldValue) {
          rotateTl.pause();
          rotateToStar(group);

          //rotateTl.setToAngle(9);
        }
      });
    });

    Platform.runLater(() -> {
      setIsRotating(targetsWrapperProperty().isEmpty());
    });

    this.getChildren().add(group);
  }

  public ShipComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID,
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

    setColor(Color.GRAY);
    Polygon ship = new Polygon();
    ship.getPoints().addAll(new Double[]{
        -10.0, -10.0,
        10.0, 0.0,
        0.0, 10.0});
    ship.fillProperty().bind(colorProperty());

    group.getChildren().add(ship);
    group.setMouseTransparent(true);

    rotateTl = new RotateTransition(Duration.seconds(5), group);
    rotateTl.setToAngle(360);
    rotateTl.setCycleCount(RotateTransition.INDEFINITE);
    rotateTl.setInterpolator(Interpolator.LINEAR);

    Platform.runLater(() -> {
      targetsWrapperProperty().addListener((observable, oldValue, newValue) -> {
        //Logger.debug("SHIP GOT: " + targetsWrapperProperty().toString());
        Logger.debug("SOMETHING CHANGED " + oldValue + " " + newValue);
        if (targetsWrapperProperty().isEmpty() && !isIsRotating()) {
          setIsRotating(true);
        } else if (!targetsWrapperProperty().isEmpty() && isIsRotating()) {
          setIsRotating(false);

        } else {
          rotateToStar(group);
        }
      });

      isRotatingProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && !oldValue) {
          group.getTransforms().add(new Translate(-fromCenterFleet, -fromCenterFleet));
          group.getTransforms().add(new Rotate(90));
          rotateTl.play();
        } else if (!newValue && oldValue) {
          rotateTl.pause();
          rotateToStar(group);

          //rotateTl.setToAngle(9);
        }
      });
    });

    Platform.runLater(() -> {
      setIsRotating(targetsWrapperProperty().isEmpty());
    });

    this.getChildren().add(group);
    this.setNumberOfShips(numberOfShips);

    Platform.runLater(() -> {
      Logger.debug("CREATING SHIP LABEL");
      Label amount = new Label();
      amount.setTextFill(Color.WHITE);

      amount.textProperty().bind(Bindings.createStringBinding(() ->
          this.numberOfShipsProperty().getValue().toString(), numberOfShipsProperty()));
      group.getChildren().add(amount);
    });

  }

  private void rotateToStar(Group group) {
    group.getTransforms().clear();
    //group.getTransforms().add(new Rotate(-group.getRotate() + 45));
    double xShip = getX();
    double yShip = getY();
    Platform.runLater(() -> {
      Star star = gamestore.stars().getByID(targetsWrapper.get(0)).get().use(Function.identity());

      double xStar = star.getCoordinates().getX();
      double yStar = star.getCoordinates().getY();

      Logger.debug("xSTAR: " + xStar + " - " + xShip);
      Logger.debug("ySTAR: " + yStar + " - " + yShip);

      double xD = xStar - xShip;
      double yD = yStar - yShip;

      double angle = Math.atan(yD / xD);

      Logger.debug("ROTATING: " + angle);
      Logger.debug("ROTATING: " + Math.toDegrees(angle));
      Logger.debug("xD: " + xD);
      Logger.debug("yD: " + yD);

      RotateTransition rotateTl = new RotateTransition(Duration.seconds(0.2), group);
      if (xD < 0) {
        rotateTl.setToAngle(Math.toDegrees(angle) + 90 + 180 + 45);
        //group.getTransforms().add(new Rotate(Math.toDegrees(angle) + 90 + 180));
      } else {
        rotateTl.setToAngle(Math.toDegrees(angle) + 90 + 45);
        //group.getTransforms().add(new Rotate(Math.toDegrees(angle) + 90));
      }
      rotateTl.play();
    });

  }

  public GameStore getGamestore() {
    return gamestore;
  }

  public void setGamestore(GameStore gamestore) {
    this.gamestore = gamestore;
  }

  public ObservableList<String> getTargetsWrapper() {
    return targetsWrapper.get();
  }

  public void setTargetsWrapper(List<String> targetIDs) {
    this.targetIDs.setAll(targetIDs);
  }

  public ListProperty<String> targetsWrapperProperty() {
    return targetsWrapper;
  }

  public double getX() {
    return x.get();
  }

  public void setX(double x) {
    this.x.set(x);
  }

  public DoubleProperty xProperty() {
    return x;
  }

  public double getY() {
    return y.get();
  }

  public void setY(double y) {
    this.y.set(y);
  }

  public DoubleProperty yProperty() {
    return y;
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

  public int getNumberOfShips() {
    return numberOfShips.get();
  }

  public IntegerProperty numberOfShipsProperty() {
    return numberOfShips;
  }

  public void setNumberOfShips(int numberOfShips) {
    this.numberOfShips.set(numberOfShips);
  }

}
