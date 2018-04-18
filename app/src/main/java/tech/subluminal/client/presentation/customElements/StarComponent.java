package tech.subluminal.client.presentation.customElements;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class StarComponent extends Pane {

  public static final int BORDER_WIDTH = 3;
  private final int sizeAll = 60;
  private final DoubleProperty sizeProperty = new SimpleDoubleProperty();
  private final DoubleProperty xProperty = new SimpleDoubleProperty();
  private final DoubleProperty yProperty = new SimpleDoubleProperty();
  private final StringProperty starID = new SimpleStringProperty();
  private final StringProperty ownerID = new SimpleStringProperty();
  private final DoubleProperty possession = new SimpleDoubleProperty();
  private final ObjectProperty color = new SimpleObjectProperty();


  private final IntegerProperty parentWidthProperty = new SimpleIntegerProperty();
  private final IntegerProperty parentHeightProperty = new SimpleIntegerProperty();

  private final String name;
  private final Group border;

  //private final ObjectProperty

  public StarComponent(String ownerID, double possession, Coordinates coordinates, String id) {

    setPossession(possession);
    setXProperty(coordinates.getX());
    setYProperty(coordinates.getY());
    setSizeProperty(0.2);
    setColor(color);
    setStarID(id);
    setOwnerID(ownerID);

    setColor(Color.GRAY);

    this.layoutXProperty().bind(Bindings
        .createDoubleBinding(
            () -> parentWidthProperty.doubleValue() / 2 + (xProperty.doubleValue() - 0.5) * Math
                .min(parentWidthProperty.doubleValue(), parentHeightProperty.doubleValue()),
            xProperty, parentWidthProperty, parentHeightProperty));
    this.layoutYProperty().bind(Bindings
        .createDoubleBinding(
            () -> parentHeightProperty.doubleValue() / 2 + (yProperty.doubleValue() - 0.5) * Math
                .min(parentWidthProperty.doubleValue(), parentHeightProperty.doubleValue()),
            yProperty, parentWidthProperty, parentHeightProperty));
    Platform.runLater(() -> {
      this.parentWidthProperty.bind(getScene().widthProperty());
      this.parentHeightProperty.bind(getScene().heightProperty());
    });

    this.name = "SUBBY";

    Circle star = new Circle();
    star.setFill(Color.GRAY);
    //star.setCenterY(sizeAll / 2);
    //star.setCenterX(sizeAll / 2);
    star.radiusProperty().bind(Bindings.createDoubleBinding(
        () -> sizeProperty.doubleValue() * sizeAll, sizeProperty));

    Circle possessionCount = new Circle();
    possessionCount.setOpacity(0.7);
    possessionCount.setFill(Color.GRAY);
    possessionCount.fillProperty().bind(colorProperty());
    //possessionCount.setCenterX(sizeAll / 2);
    //possessionCount.setCenterY(sizeAll / 2);
    possessionCount.radiusProperty().bind(Bindings
        .createDoubleBinding(() -> star.getRadius() * Math.pow(getPossession(), 0.8),
            possessionProperty(), sizeProperty));

    Pane starGroup = new Pane();
    starGroup.setPrefWidth(sizeAll);
    starGroup.setPrefHeight(sizeAll);
    //starGroup.setTranslateX(-sizeAll / 2);
    //starGroup.setTranslateY(-sizeAll / 2);

    border = makeBorder();
    border.setVisible(false);
    starGroup.setOnMouseEntered(event -> border.setVisible(true));

    starGroup.setOnMouseExited(event -> border.setVisible(false));

    Label starName = new Label(name);
    starName.getStyleClass().add("starname-label");
    starName.setAlignment(Pos.BOTTOM_CENTER);
    starName.setPrefWidth(sizeAll);
    starName.setTextAlignment(TextAlignment.CENTER);

    starGroup.getChildren().addAll(star, starName, border, possessionCount);
    this.getChildren().addAll(starGroup);

    //this.getChildren().addAll(star, starName);

    //star.fillProperty().bind(colorProperty);

  }

  public Object getColor() {
    return color.get();
  }

  public void setColor(Object color) {
    this.color.set(color);
  }

  public ObjectProperty colorProperty() {
    return color;
  }

  public String getName() {
    return name;
  }

  public double getSizeProperty() {
    return sizeProperty.get();
  }

  public void setSizeProperty(double sizeProperty) {
    this.sizeProperty.set(sizeProperty);
  }

  public DoubleProperty sizePropertyProperty() {
    return sizeProperty;
  }

  public double getxProperty() {
    return xProperty.get();
  }

  public DoubleProperty xPropertyProperty() {
    return xProperty;
  }

  public void setXProperty(double xProperty) {
    this.xProperty.set(xProperty);
  }

  public double getyProperty() {
    return yProperty.get();
  }

  public DoubleProperty yPropertyProperty() {
    return yProperty;
  }

  public void setYProperty(double yProperty) {
    this.yProperty.set(yProperty);
  }

  public String getOwnerID() {
    return ownerID.get();
  }

  public void setOwnerID(String ownerIDProperty) {
    this.ownerID.set(ownerIDProperty);
  }

  public StringProperty ownerIDProperty() {
    return ownerID;
  }

  public String getStarID() {
    return starID.get();
  }

  public void setStarID(String starID) {
    this.starID.set(starID);
  }

  public StringProperty starIDProperty() {
    return starID;
  }

  public double getPossession() {
    return possession.get();
  }

  public void setPossession(double possession) {
    this.possession.set(possession);
  }

  public DoubleProperty possessionProperty() {
    return possession;
  }

  private Group makeBorder() {
    Group border = new Group();

    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        Rectangle focus = new Rectangle(BORDER_WIDTH, sizeAll / 5);

        focus.setFill(Color.RED);
        focus.setX(x * (sizeAll - BORDER_WIDTH) - (sizeAll / 2));
        focus.setY(y * (sizeAll - sizeAll / 5) - (sizeAll / 2));
        border.getChildren().add(focus);
      }
    }
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        Rectangle focus = new Rectangle(sizeAll / 5, BORDER_WIDTH);

        focus.setFill(Color.RED);
        focus.setX(x * (sizeAll - sizeAll / 5) - (sizeAll / 2));
        focus.setY(y * (sizeAll - BORDER_WIDTH) - (sizeAll / 2));
        border.getChildren().add(focus);
      }
    }

    return border;
  }
}
