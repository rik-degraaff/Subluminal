package tech.subluminal.shared.util;

import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
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


    return new MeshView(mesh);
  }
}
