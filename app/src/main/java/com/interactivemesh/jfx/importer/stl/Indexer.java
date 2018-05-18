//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactivemesh.jfx.importer.stl;

import com.interactivemesh.jfx.importer.stl.Normalizer.Triplef;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

final class Indexer {
  private int[] rfIndsArray = null;
  private float[] rfVertsArray = null;

  Indexer() {
  }

  void clear() {
    this.rfIndsArray = null;
    this.rfVertsArray = null;
  }

  void close() {
  }

  int[] getIndices() {
    return this.rfIndsArray;
  }

  float[] getIndexedVerts() {
    return this.rfVertsArray;
  }

  void createRFTuples3fAndInds(float[] var1) {
    int var2 = var1.length / 3;
    this.rfIndsArray = new int[var2];
    int var3 = 0;
    HashMap var4 = new HashMap(var2);

    Triplef var6;
    for(int var7 = 0; var7 < var2; ++var7) {
      var6 = new Triplef(var1[var7 * 3], var1[var7 * 3 + 1], var1[var7 * 3 + 2]);
      Integer var5 = (Integer)var4.get(var6);
      if (var5 == null) {
        this.rfIndsArray[var7] = var3;
        var4.put(var6, var3);
        ++var3;
      } else {
        this.rfIndsArray[var7] = var5;
      }
    }

    this.rfVertsArray = new float[var4.size() * 3];
    Set var10 = var4.entrySet();

    for(Iterator var8 = var10.iterator(); var8.hasNext(); this.rfVertsArray[var3 + 2] = var6.z) {
      Entry var9 = (Entry)var8.next();
      var3 = (Integer)var9.getValue() * 3;
      var6 = (Triplef)var9.getKey();
      this.rfVertsArray[var3] = var6.x;
      this.rfVertsArray[var3 + 1] = var6.y;
    }

  }
}
