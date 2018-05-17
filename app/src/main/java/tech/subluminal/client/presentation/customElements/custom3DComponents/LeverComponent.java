package tech.subluminal.client.presentation.customElements.custom3DComponents;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import tech.subluminal.shared.util.MeshImporter;

public class LeverComponent extends Pane {
  private static final String MESH_PATH = "/tech/subluminal/resources/3D/lever.stl";

  private static final double MODEL_SCALE_FACTOR = 20;
  private static final double MODEL_X_OFFSET = 0; // standard
  private static final double MODEL_Y_OFFSET = 0; // standard
  private static final int VIEWPORT_SIZE = 200;
  public LeverComponent(){
    MeshView meshView = MeshImporter.importMesh(MESH_PATH);

    //meshView.setTranslateX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET + 50);
    //meshView.setTranslateY(VIEWPORT_SIZE / 4 + MODEL_Y_OFFSET - 300);
    //meshView.setTranslateZ(50);

    meshView.setScaleX(MODEL_SCALE_FACTOR);
    meshView.setScaleY(MODEL_SCALE_FACTOR);
    meshView.setScaleZ(MODEL_SCALE_FACTOR);

    PhongMaterial material = new PhongMaterial(Color.BLACK);
    //meshView.setMaterial(material);

    this.getChildren().addAll(meshView);

    this.getTransforms().addAll(
        new Rotate(90, Rotate.Y_AXIS),
        new Translate(0,50,60));

    this.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));

  }
}
