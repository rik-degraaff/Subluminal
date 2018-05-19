package tech.subluminal.client.presentation.customElements;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class Jump extends Group {

  private final ObjectProperty<Color> color = new SimpleObjectProperty<Color>();

  Line line = new Line();

  private int OFFSET_DEFAULT = 5;

  public Jump(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX,
      DoubleProperty endY) {
    Double endAngle = getAngle(new Coordinates(startX.get(), startY.get()),
        new Coordinates(endX.get(), endY.get()));

    line.startXProperty().bind(startX);
    line.startYProperty().bind(startY);
    line.endXProperty().bind(endX);
    line.endYProperty().bind(endY);

    Polygon arrow = new Polygon();
    arrow.getPoints().addAll(new Double[]{
        0.0, 0.0,
        -20.0, -10.0,
        -20.0, 10.0});

    int dirX;
    if (startX.getValue() < endX.getValue()) {
      dirX = 1;
    } else {
      dirX = -1;
    }

    int dirY;
    if (startY.getValue() < endY.getValue()) {
      dirY = 1;
    } else {
      dirY = -1;
    }

    double deltaX = OFFSET_DEFAULT * Math.sin(endAngle);
    double deltaY = OFFSET_DEFAULT * Math.cos(endAngle);

    arrow.layoutXProperty().bind(Bindings.createDoubleBinding(() -> endX.getValue() + deltaX*dirX, endX));
    arrow.layoutYProperty().bind(Bindings
        .createDoubleBinding(() -> endY.getValue() + deltaY*dirY, endY));
    //arrow.layoutXProperty().
    arrow.fillProperty().bind(color);

    arrow.getTransforms().add(new Rotate(endAngle));

    line.setStroke(Color.RED);
    line.strokeProperty().bind(color);

    line.setStrokeWidth(5);

    setColor(Color.RED);

    this.getChildren().addAll(line, arrow);
  }

  public Jump(double startX, double startY, double endX, double endY) {
    Double endAngle = getAngle(new Coordinates(startX, startY), new Coordinates(endX, endY));
    line.setStartX(startX);
    line.setStartY(startY);
    line.setEndX(endX);
    line.setEndY(endY);

    Polygon arrow = new Polygon();
    arrow.getPoints().addAll(new Double[]{
        0.0, 0.0,
        -20.0, -10.0,
        -20.0, 10.0});

    arrow.setLayoutX(endX);
    arrow.setLayoutY(endY);
    arrow.fillProperty().bind(color);
    arrow.getTransforms()
        .add(new Rotate(endAngle));

    line.fillProperty().bind(color);
    line.setStroke(Color.RED);
    line.setStrokeWidth(5);

    setColor(Color.RED);

    this.getChildren().addAll(line, arrow);

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
