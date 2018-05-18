package tech.subluminal.shared.util;


import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 * Helper class to import 3d meshes
 */
public class MeshImporter {


  /**
   * Imports a specified mesh from a stl format file.
   * @param url of the model to import.
   * @return a meshview containing the mesh.
   */
  public static MeshView importMesh(String url){
    StlMeshImporter importer = new StlMeshImporter();
    importer.read(MeshImporter.class.getResource(url));
    TriangleMesh mesh = importer.getImport();

    TriangleMesh mesh1 = new TriangleMesh();

    String points = mesh.getPoints().toString();
    for(int i = 0; i < mesh.getPoints().size(); i++){
      points += mesh.getPoints().get(i);
    }

    mesh1.getPoints().addAll(mesh.getPoints());
    mesh1.getFaces().addAll(mesh.getFaces());
    mesh1.getTexCoords().addAll(mesh.getTexCoords());
    mesh1.getFaceSmoothingGroups().addAll(mesh.getFaceSmoothingGroups());
    mesh1.getNormals().addAll(mesh.getNormals());
    mesh1.setVertexFormat(mesh.getVertexFormat());

    return new MeshView(mesh1);
  }
}
