package tech.subluminal.client.presentation.customElements.custom3DComponents;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * 3D Model of the arc which holds the glass of the spaceship
 */
public class ArcComponent extends Group {

  private final DoubleProperty height = new SimpleDoubleProperty();
  private final DoubleProperty width = new SimpleDoubleProperty();
  private final DoubleProperty depth = new SimpleDoubleProperty();
  private static final double DEFAULT_HEIGHT = 500;
  private static final double DEFAULT_WIDTH = 100;
  private static final double DEFAULT_DEPTH = 200;


  public ArcComponent(){
    setHeight(DEFAULT_HEIGHT);
    setWidth(DEFAULT_WIDTH);
    setDepth(DEFAULT_DEPTH);



    Box downBox = new Box();
    //downBox.setRotate(90);

    Box upBox = new Box();
    //upBox.setRotate(90);

    downBox.heightProperty().bind(Bindings.createDoubleBinding(() -> 1/3 * getHeight(), heightProperty()));
    downBox.widthProperty().bind(widthProperty());

    upBox.heightProperty().bind(Bindings.createDoubleBinding(() -> 2/3 * getHeight(), heightProperty()));
    upBox.widthProperty().bind(widthProperty());


    //downBox.depthProperty().bind(depthProperty());
    upBox.depthProperty().bind(depthProperty());


    PhongMaterial material = new PhongMaterial(Color.GREY);
    //downBox.setMaterial(material);
    //upBox.setMaterial(material);


    this.getChildren().addAll(downBox,upBox);

  }

  public double getWidth() {
    return width.get();
  }

  public DoubleProperty widthProperty() {
    return width;
  }

  public void setWidth(double width) {
    this.width.set(width);
  }

  public double getHeight() {
    return height.get();
  }

  public DoubleProperty heightProperty() {
    return height;
  }

  public void setHeight(double height) {
    this.height.set(height);
  }

  public double getDepth() {
    return depth.get();
  }

  public DoubleProperty depthProperty() {
    return depth;
  }

  public void setDepth(double depth) {
    this.depth.set(depth);
  }
}
