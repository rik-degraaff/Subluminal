//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactivemesh.jfx.importer.stl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

final class MeshSmoother {
  private static final int FIRST_SMGR_ID = 1;
  private static final int NUM_SMGR_IDS = 32;
  private int[] triangleDone = null;

  MeshSmoother() {
  }

  void clear() {
  }

  void close() {
    this.triangleDone = null;
  }

  ArrayList<MeshSmoother.SmoothMesh> create(int[] var1, int var2, int[] var3) {
    int[] var4 = this.createNeighbours(var1);
    this.createSmoothNeighbours(var4, var1, var3);
    return this.createSmoothSubMeshesList3(var1, var2, var4);
  }

  private ArrayList<MeshSmoother.SmoothMesh> createSmoothSubMeshesList3(int[] var1, int var2, int[] var3) {
    boolean var4 = false;
    boolean var5 = false;
    boolean var6 = false;
    boolean var7 = false;
    int var11 = var1.length / 3;
    ArrayList var12 = new ArrayList();
    if (this.triangleDone == null || this.triangleDone.length < var11) {
      this.triangleDone = new int[var11];
    }

    Arrays.fill(this.triangleDone, -2);
    int[][] var13 = new int[var2][];

    for(int var14 = 0; var14 < var11; ++var14) {
      if (this.triangleDone[var14] == -2) {
        ArrayList var15 = new ArrayList();
        MeshSmoother.SmoothMesh var16 = new MeshSmoother.SmoothMesh(var15);
        byte var25 = 0;
        int var29 = var14 * 3;
        int var9 = var29 + 1;
        int var10 = var29 + 2;
        var15.add(var29);
        var15.add(var9);
        var15.add(var10);
        this.triangleDone[var14] = 0;
        int var26 = var25 + 3;
        if (var3[var29] <= -1 && var3[var9] <= -1 && var3[var10] <= -1) {
          var16.setAsSingleTriangle();
        } else {
          for(int var17 = 0; var17 < var26; ++var17) {
            int var27 = (Integer)var15.get(var17);
            if (var3[var27] > -1) {
              int var28 = (int)Math.floor((double)(var3[var27] / 3));
              if (this.triangleDone[var28] == -2) {
                var29 = var28 * 3;
                var15.add(var29);
                var15.add(var29 + 1);
                var15.add(var29 + 2);
                this.triangleDone[var28] = 0;
                var26 += 3;
              }
            } else {
              int var18 = var1[var27];
              int var19 = var12.size();
              int[] var20 = var13[var18];
              if (var20 == null) {
                var20 = new int[7];
                Arrays.fill(var20, -1);
                var20[0] = var19;
                var13[var18] = var20;
              } else {
                int var21 = -1;
                int var22 = 0;

                for(int var23 = var20.length; var22 < var23; ++var22) {
                  var21 = var20[var22];
                  if (var21 == var19 || var21 < 0) {
                    break;
                  }

                  MeshSmoother.SmoothMesh var24 = (MeshSmoother.SmoothMesh)var12.get(var21);
                  var24.setSmGrBitOn(var16);
                }

                if (var21 < 0) {
                  var20[var22] = var19;
                } else if (var21 != var19) {
                  int[] var30 = new int[var20.length + 2];
                  System.arraycopy(var20, 0, var30, 0, var20.length);
                  var30[var20.length] = var19;
                  var13[var18] = var30;
                }
              }
            }
          }
        }

        var12.add(var16);
        var16.determineSmGr();
      }
    }

    return var12;
  }

  private void createSmoothNeighbours(int[] var1, int[] var2, int[] var3) {
    boolean var4 = false;
    boolean var5 = false;
    int var6 = 0;

    for(int var7 = var3.length; var6 < var7; ++var6) {
      int var13 = var1[var6];
      if (var13 >= 0) {
        var5 = false;
        int var9;
        if (var6 % 3 == 2) {
          var9 = var6 - 2;
        } else {
          var9 = var6 + 1;
        }

        int var11;
        if (var13 % 3 == 2) {
          var11 = var13 - 2;
        } else {
          var11 = var13 + 1;
        }

        int var12;
        if (var13 % 3 == 2) {
          var12 = var13 - 2;
        } else {
          var12 = var13 + 1;
        }

        if (var2[var6] == var2[var12]) {
          if (var3[var6] != var3[var11]) {
            var5 = true;
          } else if (var3[var9] != var3[var13]) {
            var5 = true;
          }
        } else if (var3[var6] != var3[var13]) {
          var5 = true;
        } else if (var3[var9] != var3[var11]) {
          var5 = true;
        }

        if (var5) {
          var1[var6] = -1;
          var1[var13] = -1;
        }
      }
    }

  }

  private int[] createNeighbours(int[] var1) {
    int var2 = var1.length;
    int[] var3 = new int[var2];
    Arrays.fill(var3, -2);
    int var4 = 20 + var2 / 20;
    HashMap var5 = new HashMap(var4);
    var5.put(new MeshSmoother.Edge(var1[0], var1[1]), 0);
    var5.put(new MeshSmoother.Edge(var1[1], var1[2]), 1);
    var5.put(new MeshSmoother.Edge(var1[2], var1[0]), 2);

    for(int var10 = 3; var10 < var2; var10 += 3) {
      MeshSmoother.Edge var7 = new MeshSmoother.Edge(var1[var10], var1[var10 + 1]);
      Integer var6 = (Integer)var5.remove(var7);
      if (var6 == null) {
        var5.put(var7, var10);
      } else {
        var3[var10] = var6;
        var3[var6] = var10;
      }

      MeshSmoother.Edge var8 = new MeshSmoother.Edge(var1[var10 + 1], var1[var10 + 2]);
      var6 = (Integer)var5.remove(var8);
      if (var6 == null) {
        var5.put(var8, var10 + 1);
      } else {
        var3[var10 + 1] = var6;
        var3[var6] = var10 + 1;
      }

      MeshSmoother.Edge var9 = new MeshSmoother.Edge(var1[var10 + 2], var1[var10]);
      var6 = (Integer)var5.remove(var9);
      if (var6 == null) {
        var5.put(var9, var10 + 2);
      } else {
        var3[var10 + 2] = var6;
        var3[var6] = var10 + 2;
      }
    }

    return var3;
  }

  private static final class Edge {
    private final int a;
    private final int b;
    private final int hashCode;

    private Edge(int var1, int var2) {
      this.a = var1;
      this.b = var2;
      this.hashCode = var1 + var2;
    }

    public boolean equals(Object var1) {
      MeshSmoother.Edge var2 = (MeshSmoother.Edge)var1;
      return this.a == var2.a && this.b == var2.b || this.a == var2.b && this.b == var2.a;
    }

    public int hashCode() {
      return this.hashCode;
    }
  }

  static final class SmoothMesh {
    private ArrayList<Integer> smoothMeshIndices;
    private final BitSet smGrSet;
    private int smGr;
    int smGrN;
    private boolean isSingle;

    private SmoothMesh(ArrayList<Integer> var1) {
      this.smoothMeshIndices = null;
      this.smGrSet = new BitSet(33);
      this.smGr = -1;
      this.smGrN = 0;
      this.isSingle = false;
      this.smoothMeshIndices = var1;
    }

    ArrayList<Integer> getIndices() {
      return this.smoothMeshIndices;
    }

    int getSmGrId() {
      return this.smGr;
    }

    void close() {
      this.smoothMeshIndices = null;
    }

    private void setAsSingleTriangle() {
      this.isSingle = true;
    }

    private void determineSmGr() {
      if (this.isSingle) {
        this.smGr = 0;
      } else {
        this.smGrN = this.smGrSet.nextClearBit(1);
        this.smGr = 1 << this.smGrN - 1;
      }

    }

    private void setSmGrBitOn(MeshSmoother.SmoothMesh var1) {
      var1.smGrSet.set(this.smGrN);
    }
  }
}
