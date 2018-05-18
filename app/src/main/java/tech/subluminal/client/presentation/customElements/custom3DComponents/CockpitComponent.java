package tech.subluminal.client.presentation.customElements.custom3DComponents;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import tech.subluminal.shared.util.MeshImporter;

public class CockpitComponent extends Group {

  private static final String MESH_PATH = "/tech/subluminal/resources/3D/cockpit.stl";

  private static final double MODEL_SCALE_FACTOR = 170;
  private static final double MODEL_X_OFFSET = 0; // standard
  private static final double MODEL_Y_OFFSET = 0; // standard
  private static final int VIEWPORT_SIZE = 200;

  public CockpitComponent() {
    MeshView meshView = MeshImporter.importMesh(MESH_PATH);

    //meshView.setTranslateX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET + 50);
    //meshView.setTranslateY(VIEWPORT_SIZE / 4 + MODEL_Y_OFFSET - 300);
    //meshView.setTranslateZ(50);

    meshView.setScaleX(MODEL_SCALE_FACTOR);
    meshView.setScaleY(MODEL_SCALE_FACTOR);
    meshView.setScaleZ(MODEL_SCALE_FACTOR);

    PhongMaterial material = new PhongMaterial(Color.GREY);
    meshView.setMaterial(material);

    this.getChildren().addAll(meshView);

    Platform.runLater(() -> {
      this.translateXProperty().bind(Bindings
          .createDoubleBinding(() -> getScene().getWidth() / 2, getScene().widthProperty()));
      this.translateYProperty().bind(Bindings
          .createDoubleBinding(() -> getScene().getHeight() / 2 + 100, getScene().heightProperty()));
    });



    this.getTransforms().addAll(new Rotate(90, Rotate.Y_AXIS), new Rotate(-90, Rotate.X_AXIS));

    //this.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));

  }

}
