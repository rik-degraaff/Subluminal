package tech.subluminal.client.presentation.customElements;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class Jump extends Group {

  private final ObjectProperty<Color> color = new SimpleObjectProperty<Color>();

  private final DoubleProperty newStartX = new SimpleDoubleProperty();
  private Line line = new Line();
  private DoubleProperty newStartY = new SimpleDoubleProperty();
  private DoubleProperty newEndX = new SimpleDoubleProperty();
  private DoubleProperty newEndY = new SimpleDoubleProperty();
  private int OFFSET_DEFAULT = 5;

  public Jump(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX,
      DoubleProperty endY) {
    double endAngle = getAngle(new Coordinates(startX.get(), startY.get()),
        new Coordinates(endX.get(), endY.get()));

    newStartX.bind(getDoubleXBinding(endX, startX, endAngle));
    newStartY.bind(getDoubleYBinding(endY, startY, endAngle));
    newEndX.bind(getDoubleXBinding(startX, endX, endAngle));
    newEndY.bind(getDoubleYBinding(startY, endY, endAngle));

    line.startXProperty().bind(newStartX);
    line.startYProperty().bind(newStartY);
    line.endXProperty().bind(newEndX);
    line.endYProperty().bind(newEndY);

    Polygon arrow = new Polygon();
    arrow.getPoints().addAll(new Double[]{
        0.0, 0.0,
        -20.0, -10.0,
        -20.0, 10.0});

    arrow.layoutXProperty().bind(newEndX);
    arrow.layoutYProperty().bind(newEndY);
    //arrow.layoutXProperty().
    arrow.fillProperty().bind(color);

    arrow.getTransforms().add(new Rotate(endAngle));

    line.setStroke(Color.RED);
    line.strokeProperty().bind(color);

    line.setStrokeWidth(3);

    setColor(Color.RED);

    this.getChildren().addAll(line, arrow);
  }

  private DoubleBinding getDoubleYBinding(DoubleProperty startY, DoubleProperty endY,
      double endAngle) {
    int dirY;
    if (startY.getValue() < endY.getValue()) {
      dirY = 1;
    } else {
      dirY = -1;
    }

    double deltaY = OFFSET_DEFAULT * Math.cos(endAngle);

    return Bindings
        .createDoubleBinding(() -> endY.getValue() + deltaY * dirY, endY);
  }

  private DoubleBinding getDoubleXBinding(DoubleProperty startX, DoubleProperty endX,
      double endAngle) {
    int dirX;
    if (startX.getValue() < endX.getValue()) {
      dirX = 1;
    } else {
      dirX = -1;
    }

    double deltaX = OFFSET_DEFAULT * Math.sin(endAngle);
    return Bindings
        .createDoubleBinding(() -> endX.getValue() + deltaX * dirX, endX);
  }

  private double getAngle(Coordinates start, Coordinates end) {
    double angle = Math.toDegrees(Math.atan2(end.getY() - start.getY(), end.getX() - start.getX()));

    //cause start is from bottom
    return angle;
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
