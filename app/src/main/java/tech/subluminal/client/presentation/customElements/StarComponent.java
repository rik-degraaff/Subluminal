package tech.subluminal.client.presentation.customElements;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class StarComponent extends Pane {

  public static final int BORDER_WIDTH = 3;
  private final int sizeAll = 200;
  private final DoubleProperty sizeProperty = new SimpleDoubleProperty();
  private final DoubleProperty xProperty = new SimpleDoubleProperty();
  private final DoubleProperty yProperty = new SimpleDoubleProperty();

  private final StringProperty ownerIDProperty = new SimpleStringProperty();
  private final BooleanProperty hasShipsProperty = new SimpleBooleanProperty();
  private final IntegerProperty parentWidthProperty = new SimpleIntegerProperty();
  private final IntegerProperty parentHeightProperty = new SimpleIntegerProperty();

  public String getName() {
    return name;
  }

  private final String name;

  //private final ObjectProperty

  private final Group border;

  public Color getColorProperty() {
    return colorProperty.get();
  }

  public ObjectProperty<Color> colorPropertyProperty() {
    return colorProperty;
  }

  public void setColorProperty(Color colorProperty) {
    this.colorProperty.set(colorProperty);
  }

  private final ObjectProperty<Color> colorProperty = new SimpleObjectProperty<Color>();
  //private final IntegerProperty[] shipAmpountsProperty = new SimpleIntegerProperty()[8];
  //TODO: multiple Ships

  public double getSizeProperty() {
    return sizeProperty.get();
  }

  public DoubleProperty sizePropertyProperty() {
    return sizeProperty;
  }

  public void setSizeProperty(double sizeProperty) {
    this.sizeProperty.set(sizeProperty);
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

  public String getOwnerIDProperty() {
    return ownerIDProperty.get();
  }

  public StringProperty ownerIDPropertyProperty() {
    return ownerIDProperty;
  }

  public void setOwnerIDProperty(String ownerIDProperty) {
    this.ownerIDProperty.set(ownerIDProperty);
  }

  public boolean isHasShipsProperty() {
    return hasShipsProperty.get();
  }

  public BooleanProperty hasShipsPropertyProperty() {
    return hasShipsProperty;
  }

  public void setHasShipsProperty(boolean hasShipsProperty) {
    this.hasShipsProperty.set(hasShipsProperty);
  }

  public StarComponent(double x, double y, double size, String name) {

    setXProperty(x);
    setYProperty(y);
    setSizeProperty(size);
    setColorProperty(Color.GRAY);

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

    ownerIDProperty.set(null);
    hasShipsProperty.set(false);

    this.name = name;

    Circle star = new Circle();
    star.fillProperty().bind(colorProperty);
    star.setRadius(size);
    star.setCenterY(sizeAll / 2);
    star.setCenterX(sizeAll / 2);
    star.radiusProperty().bind(Bindings.createDoubleBinding(
        () -> sizeProperty.doubleValue() *sizeAll, sizeProperty));
    star.fillProperty().bind(colorProperty);

    Pane starGroup = new Pane();
    starGroup.setPrefWidth(sizeAll);
    starGroup.setPrefHeight(sizeAll);
    starGroup.setTranslateX(-sizeAll/2);
    starGroup.setTranslateY(-sizeAll/2);

    border = makeBorder();
    border.setVisible(false);
    starGroup.setOnMouseEntered(event -> border.setVisible(true));

    starGroup.setOnMouseExited(event -> border.setVisible(false));

    Label starName = new Label(name);
    starName.getStyleClass().add("starname-label");
    starName.setAlignment(Pos.BOTTOM_CENTER);
    starName.setPrefWidth(sizeAll);
    starName.setTextAlignment(TextAlignment.CENTER);

    starGroup.getChildren().addAll(star, starName, border);
    this.getChildren().addAll(starGroup);

    //this.getChildren().addAll(star, starName);

    //star.fillProperty().bind(colorProperty);

  }

  private Group makeBorder() {
    Group border = new Group();

    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        Rectangle focus = new Rectangle(BORDER_WIDTH, sizeAll / 5);

        focus.setFill(Color.RED);
        focus.setX(x * (sizeAll - BORDER_WIDTH));
        focus.setY(y * (sizeAll - sizeAll / 5));
        border.getChildren().add(focus);
      }
    }
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        Rectangle focus = new Rectangle(sizeAll / 5, BORDER_WIDTH);

        focus.setFill(Color.RED);
        focus.setX(x * (sizeAll - sizeAll / 5));
        focus.setY(y * (sizeAll - BORDER_WIDTH));
        border.getChildren().add(focus);
      }
    }

    return border;
  }
}
