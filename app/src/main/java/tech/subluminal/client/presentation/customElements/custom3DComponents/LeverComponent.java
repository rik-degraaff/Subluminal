package tech.subluminal.client.presentation.customElements.custom3DComponents;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class LeverComponent extends Pane {

  private final int SCALE_FACTOR = 1;

  public LeverComponent() {
    this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    Cylinder cylinder = new Cylinder();
    cylinder.setScaleX(2);
    cylinder.setScaleY(2);
    cylinder.setScaleZ(2);
    cylinder.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Rotate(90, Rotate.Z_AXIS));

    Cylinder handle = new Cylinder();
    handle.setHeight(10);
    /*handle.translateXProperty().bind(Bindings.createDoubleBinding(() -> {
      return cylinder.getHeight();
    }, cylinder.heightProperty()));*/

    Sphere ball = new Sphere();
    /*ball.translateXProperty().bind(Bindings.createDoubleBinding(() -> {
      return cylinder.getHeight();
    }, cylinder.heightProperty()));*/

    //this.getChildren().addAll(cylinder, handle, ball);

    this.setScaleX(SCALE_FACTOR);
    this.setScaleY(SCALE_FACTOR);
    this.setScaleZ(SCALE_FACTOR);

    this.setTranslateX(-500);


    float h = 150;                    // Height
    float s = 300;                    // Side

    float[] points = {
        0,    0,    0,            // Point 0 - Top
        0,    h,    -s/2,         // Point 1 - Front
        -s/2, h,    0,            // Point 2 - Left
        s/2,  h,    0,            // Point 3 - Back
        0,    h,    s/2           // Point 4 - Right
    };
    float[] texCoords = {
        1, 1,
        1, 0,
        0, 1,
        0, 0
    };
    int[] faces = {
        0,0,  2,0,  1,0,          // Front left face
        0,0,  1,0,  3,0,          // Front right face
        0,0,  3,0,  4,0,          // Back right face
        0,0,  4,0,  2,0,          // Back left face
        4,0,  1,0,  2,0,          // Bottom rear face
        4,0,  3,0,  1,0           // Bottom front face
    };

    TriangleMesh mesh = new TriangleMesh();
    mesh.getPoints().setAll(points);
    //mesh.getTexCoords().setAll(texCoords);
    mesh.getTexCoords().addAll(0,0);
    mesh.getFaces().setAll(faces);

    this.getChildren().addAll(new MeshView(mesh));

  }

}
