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
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;

public class MotherShipComponent extends Group {

  private final ObjectProperty<Color> color = new SimpleObjectProperty<>();

  private final BooleanProperty isRotating = new SimpleBooleanProperty();
  private final int fromCenter = 30;
  private final StringProperty ownerID = new SimpleStringProperty();
  private final ObservableList<String> targetIDs = FXCollections.observableArrayList();
  private final ListProperty<String> targetsWrapper = new SimpleListProperty<>(targetIDs);

  private final DoubleProperty x = new SimpleDoubleProperty();
  private final DoubleProperty y = new SimpleDoubleProperty();

  private final IntegerProperty parentWidthProperty = new SimpleIntegerProperty();
  private final IntegerProperty parentHeightProperty = new SimpleIntegerProperty();
  private final RotateTransition rotateTl;

  public MotherShipComponent(double x, double y, String playerId, List<String> targetIDs) {
    Group group = new Group();
    group.getTransforms().add(new Translate(-fromCenter, -fromCenter));
    group.getTransforms().add(new Rotate(90));

    setX(x);
    setY(y);

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
        Logger.debug("MOTHERSHIP GOT: " + targetsWrapperProperty().toString());
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

    Platform.runLater(() -> {
      setIsRotating(targetsWrapperProperty().isEmpty());
    });

    this.getChildren().add(group);
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

}
