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
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
  private final int fromCenter = 15;
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
  public Pane group;
  private GameStore gamestore;

  public ShipComponent(Coordinates coordinates, String playerId, List<String> targetIDs,
      GameStore gamestore) {
    this.gamestore = gamestore;
    Pane group = new Pane();
    group.getTransforms().add(new Translate(-fromCenter, -fromCenter));
    //group.getTransforms().add(new Rotate(90));

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


    Pane ship = new Pane();
    //group.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY,Insets.EMPTY)));

    ImageView shipDetails = new ImageView();
    Image shipImageBody = new Image("/tech/subluminal/resources/100w/shipBody.png");
    ImageView shipBody = new ImageView(shipImageBody);

    shipBody.setImage(shipImageBody);

    setShipColor(shipBody);

    Image shipImageDetail = new Image("/tech/subluminal/resources/100w/shipDetails.png");
    shipBody.setFitWidth(40);
    shipBody.setFitHeight(40);
    shipBody.setImage(shipImageBody);
    //shipBody.setPreserveRatio(true);
    shipDetails.setFitWidth(40);
    shipDetails.setFitHeight(40);
    shipDetails.setImage(shipImageDetail);
    //shipDetails.setPreserveRatio(true);


    ship.getChildren().addAll(shipBody, shipDetails);
    ship.setRotate(-45);

    Platform.runLater(() -> {
      group.setLayoutX(-shipBody.getFitWidth()/2);
      group.setLayoutY(-shipBody.getFitHeight()/2);
    });

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

  private void setShipColor(ImageView shipBody) {

    ColorAdjust monochrome = new ColorAdjust();
    monochrome.hueProperty().bind(Bindings.createDoubleBinding(() -> colorProperty().getValue().getHue(), colorProperty()));
    //monochrome.setSaturation(color.getValue().getSaturation());
    //monochrome.setHue(color.getValue().getHue());
    //monochrome.setBrightness(color.getValue().getBrightness());

    shipBody.setEffect(monochrome);

    shipBody.setCache(true);
    shipBody.setCacheHint(CacheHint.SPEED);


  }

  public ShipComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID,
      List<String> targetIDs, GameStore gamestore) {

    this.gamestore = gamestore;
    Pane group = new Pane();
    group.getTransforms().add(new Translate(-fromCenterFleet, -fromCenterFleet));
    //group.getTransforms().add(new Rotate(90));

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

    Pane ship = new Pane();
    //group.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY,Insets.EMPTY)));
    ImageView shipBody = new ImageView();
    ImageView shipDetails = new ImageView();
    Image shipImageBody = new Image("/tech/subluminal/resources/100w/fleetBody.png");
    Image shipImageDetail = new Image("/tech/subluminal/resources/100w/fleetDetails.png");
    shipBody.setFitWidth(30);
    shipBody.setFitHeight(30);
    shipBody.setImage(shipImageBody);
    shipBody.setPreserveRatio(true);
    shipDetails.setFitWidth(30);
    shipDetails.setFitHeight(30);
    shipDetails.setImage(shipImageDetail);
    shipDetails.setPreserveRatio(true);


    setShipColor(shipBody);

    ship.getChildren().addAll(shipBody, shipDetails);
    ship.setRotate(-45);

    Platform.runLater(() -> {
      group.setLayoutX(-shipBody.getFitWidth()/2);
      group.setLayoutY(-shipBody.getFitHeight()/2);
    });

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

    Logger.debug("CREATING SHIP LABEL");
    Label amount = new Label();
    amount.setTextFill(Color.WHITE);

    amount.textProperty().bind(Bindings.createStringBinding(() ->
        this.numberOfShipsProperty().getValue().toString(), numberOfShipsProperty()));
    group.getChildren().add(amount);



  }

  private void rotateToStar(Pane group) {
    group.getTransforms().clear();
    //group.getTransforms().add(new Rotate(-group.getRotate() + 45));
    double xShip = getX();
    double yShip = getY();
    Platform.runLater(() -> {
      Logger.debug("ALL STARS" + gamestore.stars().toString());
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

  public void setNumberOfShips(int numberOfShips) {
    this.numberOfShips.set(numberOfShips);
  }

  public IntegerProperty numberOfShipsProperty() {
    return numberOfShips;
  }

}
