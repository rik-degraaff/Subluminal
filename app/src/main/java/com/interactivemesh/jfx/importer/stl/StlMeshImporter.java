//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactivemesh.jfx.importer.stl;

import com.interactivemesh.jfx.importer.Importer;
import java.io.File;
import java.net.URL;
import java.util.EnumSet;
import javafx.scene.shape.TriangleMesh;

public class StlMeshImporter implements Importer {
  private final StlMeshImporterImpl importerImpl = new StlMeshImporterImpl(this);

  public StlMeshImporter() {
  }

  public double getCreaseAngle() {
    return this.importerImpl.getCreaseAngle();
  }

  public void setCreaseAngle(double var1) {
    this.importerImpl.setCreaseAngle(var1);
  }

  public EnumSet<StlImportOption> getOptions() {
    return this.importerImpl.getOptions();
  }

  public void setOptions(StlImportOption... var1) {
    this.importerImpl.setOptions(var1);
  }

  public void setOptions(EnumSet<StlImportOption> var1) {
    this.importerImpl.setOptions(var1);
  }

  public void read(File var1) {
    this.importerImpl.read(var1);
  }

  public void read(String var1) {
    this.importerImpl.read(var1);
  }

  public void read(URL var1) {
    this.importerImpl.read(var1);
  }

  public void onFileImported() {
  }

  public TriangleMesh getImport() {
    return this.importerImpl.getImport();
  }

  public String getSolidName() {
    return this.importerImpl.getSolidName();
  }

  public void clear() {
    this.importerImpl.clear();
  }

  public void close() {
    this.importerImpl.close();
  }
}
