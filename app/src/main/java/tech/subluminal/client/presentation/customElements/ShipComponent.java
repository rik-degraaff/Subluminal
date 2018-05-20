package tech.subluminal.client.presentation.customElements;

import java.util.List;
import java.util.function.Function;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.DrawingUtils;

public abstract class ShipComponent extends Pane {

  private static final Integer SHIP_HEIGHT = 40;
  private static final Integer FLEET_SIZE = 30;

  private final ObjectProperty<Color> color = new SimpleObjectProperty<>();

  private final BooleanProperty isRotating = new SimpleBooleanProperty();
  private final int fromCenter = 15;
  private final StringProperty ownerID = new SimpleStringProperty();
  private final ObservableList<String> targetIDs = FXCollections.observableArrayList();
  private final ListProperty<String> targetsWrapper = new SimpleListProperty<>(targetIDs);

  private final DoubleProperty x = new SimpleDoubleProperty();
  private final DoubleProperty y = new SimpleDoubleProperty();

  private final IntegerProperty numberOfShips = new SimpleIntegerProperty();
  private final RotateTransition rotateTl = new RotateTransition();
  public Pane group;
  public Label amount;
  private GameStore gamestore;

  //Mothership
  public ShipComponent(Coordinates coordinates, String playerId, List<String> targetIDs,
      GameStore gamestore, MainController main) {
    this.gamestore = gamestore;

    setX(coordinates.getX());
    setY(coordinates.getY());

    Platform.runLater(() -> {
      this.layoutXProperty().bind(DrawingUtils.getXPosition(main.getPlayArea(), xProperty()));
      this.layoutYProperty().bind(DrawingUtils.getYPosition(main.getPlayArea(), yProperty()));
    });

    this.setOwnerID(playerId);

    setTargetsWrapper(targetIDs);

    setColor(Color.GRAY);

    Group ship = new Group();

    ImageView shipBody = initShipImage("/tech/subluminal/resources/100w/shipBody.png");
    setShipColor(shipBody);

    ImageView shipDetails = initShipImage("/tech/subluminal/resources/100w/shipDetails.png");

    ship.getChildren().addAll(shipBody, shipDetails);

    ship.setMouseTransparent(true);

    TranslateTransition transTl = new TranslateTransition(Duration.seconds(0.8), ship);
    transTl.setFromY(0);
    transTl.setToY(2);
    transTl.setAutoReverse(true);
    transTl.setCycleCount(TranslateTransition.INDEFINITE);

    Platform.runLater(() -> {
      targetsWrapperProperty().addListener((observable, oldValue, newValue) -> {
        System.out.println("NEW VAL" + newValue);
        if (!newValue.isEmpty()) {
          //rotate to star
          System.out.println("ROTATION" + ship.getRotate());
          Star next = gamestore.stars().getByID(targetsWrapper.get(0)).get()
              .use(Function.identity());
          double angle = getAngle(new Coordinates(this.getLayoutX(), this.getLayoutY()),
              next.getCoordinates());

          //not too much spins
          ship.getTransforms().add(new Rotate(angle));

          //stop hover
          transTl.stop();
        } else if (newValue.isEmpty()) {
          //reset rotation
          ship.getTransforms().add(new Rotate(-ship.getRotate()%360));
          //start hover
          transTl.play();
        }
      });

      this.setMouseTransparent(true);

      isRotatingProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && !oldValue) {

        }
      });
    });

    Platform.runLater(() -> {
      setIsRotating(targetsWrapperProperty().isEmpty());
    });

    this.getChildren().add(ship);

  }

  //Fleets
  public ShipComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID,
      List<String> targetIDs, GameStore gamestore, MainController main) {

    this.gamestore = gamestore;
    Pane group = new Pane();

    setX(coordinates.getX());
    setY(coordinates.getY());

    Platform.runLater(() -> {
      this.layoutXProperty().bind(DrawingUtils.getXPosition(main.getPlayArea(), xProperty()));
      this.layoutYProperty().bind(DrawingUtils.getYPosition(main.getPlayArea(), yProperty()));
    });

    this.setOwnerID(ownerID);

    setTargetsWrapper(targetIDs);

    setColor(Color.GRAY);

    Pane ship = new Pane();
    //group.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY,Insets.EMPTY)));

    ImageView shipBody = initShipImage("/tech/subluminal/resources/100w/fleetBody.png");
    shipBody.setPreserveRatio(true);

    ImageView shipDetails = initShipImage("/tech/subluminal/resources/100w/fleetDetails.png");
    shipDetails.setPreserveRatio(true);

    setShipColor(shipBody);

    ship.getChildren().addAll(shipBody, shipDetails);

    group.getChildren().add(ship);
    group.setMouseTransparent(true);

    this.setMouseTransparent(true);

    rotateTl.setDuration(Duration.seconds(5));
    rotateTl.setNode(group);
    rotateTl.setToAngle(360);
    rotateTl.setCycleCount(RotateTransition.INDEFINITE);
    rotateTl.setInterpolator(Interpolator.LINEAR);

    Platform.runLater(() -> {
      targetsWrapperProperty().addListener((observable, oldValue, newValue) -> {

      });
    });

    Platform.runLater(() -> {
      //isRotatingProperty().bind(targetsWrapperProperty().emptyProperty());
      setIsRotating(targetsWrapper.isEmpty());
    });

    this.getChildren().add(group);

    this.setNumberOfShips(numberOfShips);

    Logger.debug("CREATING SHIP LABEL");
    amount = new Label();
    amount.setTextFill(Color.WHITE);
    amount.getStyleClass().add("ship-amount");

    DropShadow ds = new DropShadow();
    ds.setOffsetY(3.0f);
    ds.setColor(Color.color(0, 0, 0));

    amount.setEffect(ds);

    amount.textProperty().bind(Bindings.createStringBinding(() ->
        this.numberOfShipsProperty().getValue().toString(), numberOfShipsProperty()));
    group.getChildren().add(amount);

    amount.rotateProperty()
        .bind(Bindings
            .createDoubleBinding(() -> -group.getRotate(), group.rotateProperty()));

  }

  private double getAngle(Coordinates start, Coordinates end) {
    double angle = Math.toDegrees(Math.atan2(end.getY() - start.getY(), end.getX() - start.getX()));

    //cause start is from bottom
    return angle;
  }

  private ImageView initShipImage(String url) {
    Image shipImageBody = new Image(url);
    ImageView image = new ImageView(shipImageBody);
    image.setFitWidth(SHIP_HEIGHT);
    image.setFitHeight(SHIP_HEIGHT);
    image.setImage(shipImageBody);
    image.setRotate(-90);
    image.setTranslateX(-SHIP_HEIGHT / 2);
    image.setTranslateY(-SHIP_HEIGHT / 2);
    return image;
  }

  private void setShipColor(ImageView shipBody) {

    ColorAdjust monochrome = new ColorAdjust();

    monochrome.hueProperty().bind(
        Bindings.createDoubleBinding(() -> (
                ((getColor().getHue() - Color.RED.getHue()) / 180) + 1) % 2 - 1,
            colorProperty()));
    //monochrome.setHue(1);

    monochrome.saturationProperty().bind(
        Bindings.createDoubleBinding(
            () -> Color.RED.getSaturation() - getColor().getSaturation(),
            colorProperty()));
    monochrome.brightnessProperty().bind(
        Bindings.createDoubleBinding(
            () -> Color.RED.getBrightness() - getColor().getBrightness(),
            colorProperty()));

    shipBody.setEffect(monochrome);

    //shipBody.setCache(true);
    //shipBody.setCacheHint(CacheHint.SPEED);

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
