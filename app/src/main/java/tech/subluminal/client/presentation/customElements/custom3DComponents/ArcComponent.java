package tech.subluminal.client.presentation.customElements.custom3DComponents;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import tech.subluminal.shared.util.MeshImporter;

public class ArcComponent extends Group {

  private static final double MODEL_SCALE_FACTOR = 60;
  private static final double MODEL_X_OFFSET = 0; // standard
  private static final double MODEL_Y_OFFSET = 0; // standard
  private static final int VIEWPORT_SIZE = 200;

  private static final String MESH_FILENAME =
      "/tech/subluminal/resources/3D/test.stl";

  public ArcComponent() {
    //System.out.println(getClass().getResource(MESH_FILENAME).toURI());

    MeshView meshView = MeshImporter.importMesh(MESH_FILENAME);

    meshView.setTranslateX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET + 50);
    meshView.setTranslateY(VIEWPORT_SIZE / 2 + MODEL_Y_OFFSET - 300);
    //meshView.setTranslateZ(-50);

    meshView.setScaleX(MODEL_SCALE_FACTOR);
    meshView.setScaleY(MODEL_SCALE_FACTOR);
    meshView.setScaleZ(MODEL_SCALE_FACTOR * 1.25);

    PhongMaterial material = new PhongMaterial(Color.DARKSLATEBLUE);
    //meshView.setRotate(90);
    this.getTransforms().addAll(new Rotate(90, Rotate.Z_AXIS), new Rotate(90, Rotate.Y_AXIS));

    this.getChildren().addAll(meshView);
    meshView.setMaterial(material);
  }
}
