package tech.subluminal.client.presentation.customElements;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class ArrowComponent extends Pane {
  private static final double DEFAULT_SIZE = 30.0;
  private final ObjectProperty fill = new SimpleObjectProperty();

  public ArrowComponent(double size, DoubleProperty yProperty){
    super();
    setFill(Color.RED);
    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);

    Rectangle rect = new Rectangle(size/2, size);
    rect.fillProperty().bind(fillProperty());
    box.getChildren().add(rect);

    Polygon head = new Polygon();
    head.getPoints().addAll(new Double[]{
        20.0, 00.0,
        0.0, 30.0,
        40.0, 30.0 });
    head.fillProperty().bind(fillProperty());

    box.getChildren().add(0, head);
    this.getChildren().add(0,box);
    Rotate rotate = new Rotate();
    rotate.setPivotX(0);
    rotate.setPivotY(0);


    yProperty.addListener(e -> {
      if(yProperty.getValue() <= 100){
        rotate.setPivotX(0);
        rotate.setPivotY(0);
        rotate.setAngle(0);
      }else{
        rotate.setPivotX(0);
        rotate.setPivotY(0);
        rotate.setAngle(180);
      }
    });




    this.getTransforms().add(rotate);
    this.getTransforms().add(new Translate(-40/2, 0));
    TranslateTransition transTl = new TranslateTransition(Duration.seconds(0.5),this);
    Double from = 20.0;
    Double to = 30.0;
    transTl.fromYProperty().bind(Bindings.createDoubleBinding(()->{
      if(rotate.getAngle() == 0){
        return from * (-1);
      }else{
        return from;
      }
    }));

    transTl.toYProperty().bind(Bindings.createDoubleBinding(()->{
      if(rotate.getAngle() == 0){
        return to * (-1);
      }else{
        return to;
      }
    }));
    
    transTl.setCycleCount(TranslateTransition.INDEFINITE);
    transTl.setAutoReverse(true);
    transTl.play();

  }

  public ArrowComponent(DoubleProperty yProperty){
    this(DEFAULT_SIZE, yProperty);
  }


  public Object getFill() {
    return fill.get();
  }

  public ObjectProperty fillProperty() {
    return fill;
  }

  public void setFill(Object fill) {
    this.fill.set(fill);
  }
}
