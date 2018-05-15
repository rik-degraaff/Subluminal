package tech.subluminal.client.presentation.customElements.custom3DComponents;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class ArcComponent extends Group {

  private DoubleProperty height = new SimpleDoubleProperty();
  private DoubleProperty depth = new SimpleDoubleProperty();
  private static final double DEFAULT_HEIGHT = 100;
  private static final double DEFAULT_DEPTH = 20;


  public ArcComponent(){
    super();


    Box downBox = new Box();
    Box upBox = new Box();

    downBox.heightProperty().bind(Bindings.createDoubleBinding(() -> 1/3 * getHeight(), heightProperty()));
    upBox.heightProperty().bind(Bindings.createDoubleBinding(() -> 2/3 * getHeight(), heightProperty()));


    downBox.depthProperty().bind(depthProperty());
    upBox.depthProperty().bind(depthProperty());


    PhongMaterial material = new PhongMaterial(Color.GREY);
    //downBox.setMaterial(material);
    //upBox.setMaterial(material);

    upBox.setRotate(20);

    this.getChildren().addAll(downBox,upBox);

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
