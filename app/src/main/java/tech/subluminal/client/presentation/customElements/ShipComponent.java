package tech.subluminal.client.presentation.customElements;

import static tech.subluminal.shared.util.FileUtils.getExtension;
import static tech.subluminal.shared.util.FileUtils.removeExtension;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.ConfigModifier;
import tech.subluminal.shared.util.DrawingUtils;

public abstract class ShipComponent extends Pane {

  public static final String DEFAULT_ANIM_EXTENTION = ".gif";
  public static final String DEFAULT_EXTENTION = ".png";

  public static final String FLEET_BODY_FILE = "fleetBody";
  public static final String DEFAULT_FLEET_BODY_URL =
      "/tech/subluminal/resources/100w/" + FLEET_BODY_FILE + DEFAULT_EXTENTION;
  public static final String FLEET_DETAILS_FILE = "fleetDetails";
  public static final String DEFAULT_FLEET_DETAILS_URL =
      "/tech/subluminal/resources/100w/" + FLEET_DETAILS_FILE + DEFAULT_ANIM_EXTENTION;
  public static final String SHIP_BODY_FILE = "shipBody";
  public static final String DEFAULT_SHIP_BODY_URL =
      "/tech/subluminal/resources/100w/" + SHIP_BODY_FILE + DEFAULT_EXTENTION;
  public static final String SHIP_DETAILS_FILE = "shipDetails";
  public static final String DEFAULT_SHIP_DETAILS_URL =
      "/tech/subluminal/resources/100w/" + SHIP_DETAILS_FILE + DEFAULT_ANIM_EXTENTION;
  public static final List<String> IMAGE_EXTENSIONS = Arrays.asList("gif", "jpg", "jpeg", "png");

  private static final Integer SHIP_SIZE = 40;

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
  private final TranslateTransition transTl = new TranslateTransition();
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

    ConfigModifier<String, String> cm = new ConfigModifier<>("mods/ships");
    List<File> shipImages = cm.getAllFiles();

    String shipBodyUrl = getModFileOrDefault(shipImages, SHIP_BODY_FILE, DEFAULT_SHIP_BODY_URL,
        IMAGE_EXTENSIONS);

    String shipDetailsUrl = getModFileOrDefault(shipImages, SHIP_DETAILS_FILE,
        DEFAULT_SHIP_DETAILS_URL,
        IMAGE_EXTENSIONS);

    ImageView shipBody = initShipImage(shipBodyUrl);

    ImageView shipDetails = initShipImage(shipDetailsUrl);

    setShipColor(shipBody);
    shipBody.setPreserveRatio(true);

    shipDetails.setPreserveRatio(true);

    ship.getChildren().addAll(shipBody, shipDetails);
    ship.setMouseTransparent(true);

    //ship.prefWidth(SHIP_SIZE);
    //ship.prefHeight(SHIP_SIZE);

    transTl.setNode(ship);
    transTl.setDuration(Duration.seconds(0.8));
    transTl.setFromY(0);
    transTl.setToY(2);
    transTl.setAutoReverse(true);
    transTl.setCycleCount(TranslateTransition.INDEFINITE);

    Platform.runLater(() -> {
      targetsWrapperProperty().addListener((observable, oldValue, newValue) -> {
        System.out.println("NEW VAL" + newValue);
        if (!newValue.isEmpty()) {
          setShipMoving(ship);
        } else if (newValue.isEmpty()) {
          setShipOnStar(ship);
        }
      });

      if (targetsWrapper.isEmpty()) {
        setShipOnStar(ship);
      } else {
        setShipMoving(ship);
      }
    });

    this.setMouseTransparent(true);
    this.getChildren().add(ship);

  }

  //Fleets
  public ShipComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID,
      List<String> targetIDs, GameStore gamestore, MainController main) {

    this.gamestore = gamestore;

    setX(coordinates.getX());
    setY(coordinates.getY());

    Platform.runLater(() -> {
      this.layoutXProperty().bind(DrawingUtils.getXPosition(main.getPlayArea(), xProperty()));
      this.layoutYProperty().bind(DrawingUtils.getYPosition(main.getPlayArea(), yProperty()));
    });

    this.setOwnerID(ownerID);

    setTargetsWrapper(targetIDs);

    setColor(Color.GRAY);

    Group ship = new Group();
    //group.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY,Insets.EMPTY)));

    ConfigModifier<String, String> cm = new ConfigModifier<>("mods/ships");
    List<File> fleetImages = cm.getAllFiles();

    String fleetBodyUrl = getModFileOrDefault(fleetImages, FLEET_BODY_FILE, DEFAULT_FLEET_BODY_URL,
        IMAGE_EXTENSIONS);

    String fleetDetailsUrl = getModFileOrDefault(fleetImages, FLEET_DETAILS_FILE,
        DEFAULT_FLEET_DETAILS_URL,
        IMAGE_EXTENSIONS);

    ImageView shipBody = initShipImage(fleetBodyUrl);

    ImageView shipDetails = initShipImage(fleetDetailsUrl);

    shipBody.setPreserveRatio(true);

    shipDetails.setPreserveRatio(true);

    setShipColor(shipBody);

    ship.getChildren().addAll(shipBody, shipDetails);

    this.setMouseTransparent(true);

    rotateTl.setDuration(Duration.seconds(7));
    rotateTl.setNode(ship);
    rotateTl.setToAngle(-360);
    rotateTl.setCycleCount(Timeline.INDEFINITE);
    rotateTl.setInterpolator(Interpolator.LINEAR);

    Platform.runLater(() -> {
      targetsWrapperProperty().addListener((observable, oldValue, newValue) -> {
        System.out.println("NEW VAL" + newValue);
        if (targetsWrapper.isEmpty()) {
          //reset rotation
          setFleetRotating(ship);
        } else {
          //rotate to star
          setFleetMoving(ship);
        }
      });

      this.setMouseTransparent(true);

      if (targetsWrapper.isEmpty()) {
        setFleetRotating(ship);
      } else {
        setFleetMoving(ship);
      }

    });

    this.getChildren().add(ship);

    this.setNumberOfShips(numberOfShips);

    Logger.debug("CREATING SHIP LABEL");
    amount = new Label();
    amount.setTextFill(Color.WHITE);
    amount.getStyleClass().add("ship-amount");

    amount.textProperty().bind(Bindings.createStringBinding(() ->
        this.numberOfShipsProperty().getValue().toString(), numberOfShipsProperty()));
    ship.getChildren().addAll(amount);

    amount.rotateProperty()
        .bind(Bindings
            .createDoubleBinding(() -> -ship.getRotate(), ship.rotateProperty()));

    Platform.runLater(() -> {
      amount.translateXProperty().bind(
          Bindings.createDoubleBinding(() -> -amount.prefWidth(-1) / 2, amount.widthProperty()));

      amount.translateYProperty().bind(
          Bindings.createDoubleBinding(() -> -amount.prefHeight(-1) / 2, amount.heightProperty()));
    });

  }

  private String getModFileOrDefault(List<File> files, String fileName, String defaultName,
      List<String> extensions) {
    return files.stream()
        .filter(f -> removeExtension(f.getName()).equals(fileName))
        .filter(f -> extensions.contains(getExtension(f.getName())))
        .map(f -> f.toURI().toString())
        .findAny()
        .orElse(defaultName);
  }

  private void setFleetMoving(Group ship) {
    ship.getTransforms().clear();
    ship.setRotate(0);
    rotateTl.stop();

    rotateToStar(ship);
    //stop hover
  }

  private void setFleetRotating(Group ship) {
    ship.setRotate(0);
    //start hover
    ship.getTransforms().clear();

    //movePivot(ship, 0, 30);
    ship.getTransforms().add(new Translate(0, -40));

    rotateTl.play();
  }

  private void setShipMoving(Group ship) {
    ship.getTransforms().clear();
    //rotate to star
    rotateToStar(ship);
    //stop hover
    transTl.stop();
  }

  private void setShipOnStar(Group ship) {
    ship.setRotate(90);
    //start hover
    ship.getTransforms().add(new Translate(-20, -20));
    transTl.play();
  }

  private void rotateToStar(Group ship) {
    Star next = gamestore.stars().getByID(targetsWrapper.get(0)).get()
        .use(Function.identity());
    double angle = getAngle(new Coordinates(xProperty().getValue(), yProperty().getValue()),
        next.getCoordinates()) - 180;

    double delta = (ship.getRotate() - (angle));

    //not too much spins
    ship.setRotate(angle % 360);
  }

  private double getAngle(Coordinates start, Coordinates end) {
    double angle = Math.toDegrees(Math.atan2(end.getY() - start.getY(), end.getX() - start.getX()));

    //cause start is from bottom
    return angle;
  }

  private ImageView initShipImage(String url) {
    Image shipImageBody = new Image(url);
    ImageView image = new ImageView(shipImageBody);
    image.setFitWidth(SHIP_SIZE);
    image.setFitHeight(SHIP_SIZE);
    image.setImage(shipImageBody);
    image.setRotate(-90);
    image.setTranslateX(-SHIP_SIZE / 2);
    image.setTranslateY(-SHIP_SIZE / 2);
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
