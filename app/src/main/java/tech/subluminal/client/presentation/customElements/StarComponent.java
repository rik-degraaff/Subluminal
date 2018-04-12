package tech.subluminal.client.presentation.customElements;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class StarComponent extends Pane {

  public static final int sizeAll = 200;
  public static final int BORDER_WIDTH = 3;
  private final IntegerProperty sizeProperty = new SimpleIntegerProperty();
  private final IntegerProperty widthProperty = new SimpleIntegerProperty();
  private final IntegerProperty heightProperty = new SimpleIntegerProperty();

  private final StringProperty ownerIDProperty = new SimpleStringProperty();
  private final BooleanProperty hasShipsProperty = new SimpleBooleanProperty();

  private final String name;

  private final Group border;

  private Paint colorProperty;
  //private final IntegerProperty[] shipAmpountsProperty = new SimpleIntegerProperty()[8];
  //TODO: multiple Ships

  public int getSizeProperty() {
    return sizeProperty.get();
  }

  public IntegerProperty sizePropertyProperty() {
    return sizeProperty;
  }

  public void setSizeProperty(int sizeProperty) {
    this.sizeProperty.set(sizeProperty);
  }

  public int getWidthProperty() {
    return widthProperty.get();
  }

  public IntegerProperty widthPropertyProperty() {
    return widthProperty;
  }

  public void setWidthProperty(int widthProperty) {
    this.widthProperty.set(widthProperty);
  }

  public int getHeightProperty() {
    return heightProperty.get();
  }

  public IntegerProperty heightPropertyProperty() {
    return heightProperty;
  }

  public void setHeightProperty(int heightProperty) {
    this.heightProperty.set(heightProperty);
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

  public Paint getColorProperty() {
    return colorProperty;
  }

  public void setColorProperty(Paint colorProperty) {
    this.colorProperty = colorProperty;
  }


  public StarComponent(int x, int y, int size, String name) {
    widthProperty.set(x);
    heightProperty.set(y);
    sizeProperty.set(size);

    ownerIDProperty.set(null);
    hasShipsProperty.set(false);
    colorProperty = Color.GRAY;

    this.name = name;

    Circle star = new Circle();
    star.setFill(colorProperty);
    star.setRadius(size);
    star.setCenterY(sizeAll / 2);
    star.setCenterX(sizeAll / 2);

    this.setLayoutX(x);
    this.setLayoutY(y);

    Pane starGroup = new Pane();
    starGroup.setPrefWidth(sizeAll);
    starGroup.setPrefHeight(sizeAll);

    border = makeBorder();
    border.setVisible(false);
    starGroup.setOnMouseEntered(event -> border.setVisible(true));

    starGroup.setOnMouseExited(event -> border.setVisible(false));

    Label starName = new Label(name);
    starName.getStyleClass().add("starname-label");
    starName.setAlignment(Pos.BOTTOM_CENTER);
    starName.setPrefWidth(sizeAll);
    starName.setTextAlignment(TextAlignment.CENTER);

    starGroup.getChildren().addAll(star, starName);
    this.getChildren().addAll(starGroup, border);

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
