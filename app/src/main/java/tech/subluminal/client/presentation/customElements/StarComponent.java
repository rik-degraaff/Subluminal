package tech.subluminal.client.presentation.customElements;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.css.StyleableObjectProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class StarComponent extends Group {

  private final IntegerProperty sizeProperty = new SimpleIntegerProperty();
  private final IntegerProperty widthProperty = new SimpleIntegerProperty();
  private final IntegerProperty heightProperty = new SimpleIntegerProperty();

  private final StringProperty ownerIDProperty = new SimpleStringProperty();
  private final BooleanProperty hasShipsProperty = new SimpleBooleanProperty();

  private Color colorProperty;
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

  public Color getColorProperty() {
    return colorProperty;
  }

  public void setColorProperty(Color colorProperty) {
    this.colorProperty = colorProperty;
  }


  public StarComponent(int x, int y, int size) {
    widthProperty.set(x);
    heightProperty.set(y);
    sizeProperty.set(size);

    ownerIDProperty.set(null);
    hasShipsProperty.set(false);
    colorProperty = Color.GRAY;

    Node parent = this.getParent();
    Circle star = new Circle();
    star.setFill(colorProperty);
    star.setCenterX(size / 2);
    star.setCenterY(size / 2);
    star.setRadius(size);
    this.getChildren().add(star);

  }
}
