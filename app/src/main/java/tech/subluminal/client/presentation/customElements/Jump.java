package tech.subluminal.client.presentation.customElements;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Jump extends Line {

  private final ObjectProperty<Color> color = new SimpleObjectProperty<Color>();

  public Jump(Property startX, Property startY, Property endX, Property endY) {
    this.startXProperty().bind(startX);
    this.startYProperty().bind(startY);
    this.endXProperty().bind(endX);
    this.endYProperty().bind(endY);

    this.fillProperty().bind(color);
    this.setStroke(Color.RED);
    this.setStrokeWidth(5);

    setColor(Color.RED);

  }

  public Jump(double startX, double startY, double endX, double endY) {
    this.setStartX(startX);
    this.setStartY(startY);
    this.setEndX(endX);
    this.setEndY(endY);

    this.fillProperty().bind(color);
    this.setStroke(Color.RED);
    this.setStrokeWidth(5);

    setColor(Color.RED);

  }

  public Color getColorProperty() {
    return color.get();
  }

  public ObjectProperty<Color> colorProperty() {
    return color;
  }

  public void setColor(Color colorObjectProperty) {
    this.color.set(colorObjectProperty);
  }

}
