package tech.subluminal.client.presentation.customElements.custom3DComponents;

import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import java.io.File;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

public class TestComponent extends Group {

  private static final double MODEL_SCALE_FACTOR = 60;
  private static final double MODEL_X_OFFSET = 0; // standard
  private static final double MODEL_Y_OFFSET = 0; // standard
  private static final int VIEWPORT_SIZE = 200;

  private static final String MESH_FILENAME =
      "/Users/davel/Desktop/Gruppe-11/app/src/main/java/tech/subluminal/resources/3D/test.stl";

  public TestComponent() {
    File file = new File(MESH_FILENAME);
    StlMeshImporter importer = new StlMeshImporter();
    importer.read(file);
    Mesh mesh = importer.getImport();

    MeshView meshView = new MeshView(mesh);
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
