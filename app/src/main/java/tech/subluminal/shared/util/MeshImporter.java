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

    //TriangleMesh mesh1 = new TriangleMesh();

    //float[] points = new float[mesh.getPoints().size()];
    //mesh.getPoints().toArray(points);

    //System.out.println(points);
    //mesh1.getPoints().addAll(points);
    //mesh1.getFaces().addAll(mesh.getFaces());
    //mesh1.getTexCoords().addAll(mesh.getTexCoords());
    //mesh1.getFaceSmoothingGroups().addAll(mesh.getFaceSmoothingGroups());
    //mesh1.getNormals().addAll(mesh.getNormals());
    //mesh1.setVertexFormat(mesh.getVertexFormat());

    return new MeshView(mesh);
  }
}
