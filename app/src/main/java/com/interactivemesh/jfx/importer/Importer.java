//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactivemesh.jfx.importer;

import java.io.File;
import java.net.URL;

public interface Importer {
  double getCreaseAngle();

  void setCreaseAngle(double var1);

  void read(File var1);

  void read(String var1);

  void read(URL var1);

  void onFileImported();

  Object getImport();

  void clear();

  void close();
}
