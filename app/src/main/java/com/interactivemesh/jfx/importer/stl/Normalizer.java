//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactivemesh.jfx.importer.stl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

final class Normalizer {
  Normalizer() {
  }

  float[] calcIndexedVertexNormals(double var1, float[] var3, float[] var4, int var5, int[] var6, int[] var7) {
    Object var8 = null;
    int[] var45;
    if ((var3 == null || var3.length < 1) && var4 != null) {
      var45 = new int[var5];
      var3 = this.calcTriangleNormals(var4, var6, var45);
    } else {
      var45 = this.calcVertTriangleCt(var5, var6);
    }

    int[][] var9 = this.vertexIndices(var6, var45);
    int var10 = (int)((float)var9.length * 1.5F);
    HashMap var11 = new HashMap(var10, 1.0F);
    Integer var12 = 0;
    float var13 = (float)Math.cos(var1);
    ArrayList var14 = new ArrayList(100);
    Normalizer.Triplef var15 = null;
    float var16 = 0.0F;
    float var17 = 0.0F;
    float var18 = 0.0F;
    float var19 = 0.0F;
    float var20 = 0.0F;
    float var21 = 0.0F;
    float var22 = 0.0F;
    float var23 = 0.0F;
    float var24 = 0.0F;
    byte var25 = 50;
    int[] var26 = new int[var25];
    int[] var27 = new int[var25];
    int[] var28 = new int[var25];
    int[] var29 = new int[var25];
    int[] var30 = new int[var25];
    int[] var31 = new int[var25];
    int var32 = 0;

    int var36;
    for(int var33 = var9.length; var32 < var33; ++var32) {
      int[] var34 = var9[var32];
      boolean var35 = false;
      int var49;
      if (var34 != null && (var49 = var34.length) >= 1) {
        if (var26.length < var49) {
          var26 = new int[var49];
          var27 = new int[var49];
          var28 = new int[var49];
          var29 = new int[var49];
          var30 = new int[var49];
          var31 = new int[var49];
        }

        Arrays.fill(var30, -2);
        Arrays.fill(var31, -2);

        int var37;
        int var38;
        for(var36 = 0; var36 < var49; ++var36) {
          var37 = var34[var36];
          var26[var36] = var37;
          var38 = var37 / 3;
          var27[var36] = var38;
          int var39 = var37 % 3;
          if (var39 == 0) {
            var28[var36] = var6[var37 + 2];
            var29[var36] = var6[var37 + 1];
          } else if (var39 == 1) {
            var28[var36] = var6[var37 - 1];
            var29[var36] = var6[var37 + 1];
          } else if (var39 == 2) {
            var28[var36] = var6[var37 - 1];
            var29[var36] = var6[var37 - 2];
          }
        }

        for(var36 = 0; var36 < var49; ++var36) {
          if (var31[var36] < 0) {
            var37 = var29[var36];

            for(var38 = var36 + 1; var38 < var49; ++var38) {
              if (var37 == var28[var38]) {
                var31[var36] = var38;
                var30[var38] = var36;
                break;
              }
            }
          }

          if (var30[var36] < 0) {
            var37 = var28[var36];

            for(var38 = var36 + 1; var38 < var49; ++var38) {
              if (var37 == var29[var38]) {
                var30[var36] = var38;
                var31[var38] = var36;
                break;
              }
            }
          }
        }

        for(var36 = 0; var36 < var49; ++var36) {
          if (var27[var36] >= 0) {
            var37 = var27[var36];
            var38 = var37 * 3;
            var27[var36] = -2;
            var16 = var3[var38];
            var17 = var3[var38 + 1];
            var18 = var3[var38 + 2];
            var15 = new Normalizer.Triplef(var16, var17, var18);
            var14.clear();
            var14.add(var26[var36]);
            int[] var52 = new int[]{var31[var36], var30[var36]};
            Object[] var40 = new Object[]{var31, var30};

            int var41;
            int var42;
            for(var41 = 0; var41 < 2; ++var41) {
              var42 = var52[var41];
              if (var42 >= 0) {
                int[] var43 = (int[])((int[])var40[var41]);
                var19 = var16;
                var20 = var17;

                for(var21 = var18; var42 >= 0; var42 = var43[var42]) {
                  var37 = var27[var42];
                  if (var37 < 0) {
                    break;
                  }

                  var38 = var37 * 3;
                  var22 = var3[var38];
                  var23 = var3[var38 + 1];
                  var24 = var3[var38 + 2];
                  float var44 = var22 * var19 + var23 * var20 + var24 * var21;
                  if (var44 <= var13) {
                    break;
                  }

                  var27[var42] = -2;
                  var14.add(var26[var42]);
                  var15.add(var22, var23, var24);
                  var19 = var22;
                  var20 = var23;
                  var21 = var24;
                }
              }
            }

            var15.normalize();
            var12 = (Integer)var11.get(var15);
            if (var12 == null) {
              var12 = new Integer(var11.size());
              var11.put(var15, var12);
            }

            var41 = 0;

            for(var42 = var14.size(); var41 < var42; ++var41) {
              var7[(Integer)var14.get(var41)] = var12;
            }
          }
        }
      }
    }

    Set var46 = var11.entrySet();
    Iterator var47 = var46.iterator();

    Normalizer.Triplef var50;
    float[] var51;
    for(var51 = new float[var11.size() * 3]; var47.hasNext(); var51[var36 + 2] = var50.z) {
      Entry var48 = (Entry)var47.next();
      var50 = (Normalizer.Triplef)var48.getKey();
      var36 = (Integer)var48.getValue() * 3;
      var51[var36] = var50.x;
      var51[var36 + 1] = var50.y;
    }

    return var51;
  }

  private int[] calcVertTriangleCt(int var1, int[] var2) {
    int[] var3 = new int[var1];
    int var7 = 0;

    for(int var8 = var2.length - 2; var7 < var8; var7 += 3) {
      int var4 = var2[var7];
      int var5 = var2[var7 + 1];
      int var6 = var2[var7 + 2];
      ++var3[var4];
      ++var3[var5];
      ++var3[var6];
    }

    return var3;
  }

  private float[] calcTriangleNormals(float[] var1, int[] var2, int[] var3) {
    float var15 = 0.0F;
    float var16 = 0.0F;
    float var17 = 0.0F;
    float var18 = 0.0F;
    float var19 = 0.0F;
    float var20 = 0.0F;
    int var21 = var2.length;
    float[] var22 = new float[var21 / 3 * 3];
    int var23 = 0;

    for(int var24 = var21 - 2; var23 < var24; var23 += 3) {
      int var4 = var2[var23];
      int var5 = var2[var23 + 1];
      int var6 = var2[var23 + 2];
      ++var3[var4];
      ++var3[var5];
      ++var3[var6];
      int var8 = var4 * 3;
      int var9 = var5 * 3;
      int var10 = var6 * 3;
      var15 = var1[var9] - var1[var8];
      var16 = var1[var9 + 1] - var1[var8 + 1];
      var17 = var1[var9 + 2] - var1[var8 + 2];
      var18 = var1[var10] - var1[var8];
      var19 = var1[var10 + 1] - var1[var8 + 1];
      var20 = var1[var10 + 2] - var1[var8 + 2];
      float var11 = var16 * var20 - var17 * var19;
      float var12 = var18 * var17 - var20 * var15;
      float var13 = var15 * var19 - var16 * var18;
      float var14 = (float)Math.sqrt((double)(var11 * var11 + var12 * var12 + var13 * var13));
      if (var14 != 0.0F) {
        var14 = 1.0F / var14;
        var22[var23] = var11 * var14;
        var22[var23 + 1] = var12 * var14;
        var22[var23 + 2] = var13 * var14;
      }
    }

    return var22;
  }

  private int[][] vertexIndices(int[] var1, int[] var2) {
    int var3 = var2.length;
    int var4 = var1.length / 3;
    int[][] var5 = new int[var3][];

    int var6;
    for(var6 = 0; var6 < var3; ++var6) {
      var5[var6] = new int[var2[var6]];
    }

    for(var6 = 0; var6 < var4; ++var6) {
      int var7 = var6 * 3;
      int var8 = var1[var7];
      var5[var8][--var2[var8]] = var7++;
      int var9 = var1[var7];
      var5[var9][--var2[var9]] = var7++;
      int var10 = var1[var7];
      var5[var10][--var2[var10]] = var7;
    }

    return var5;
  }

  static final class Triplef {
    float x = 0.0F;
    float y = 0.0F;
    float z = 0.0F;

    Triplef(float var1, float var2, float var3) {
      if (var1 == 0.0F) {
        this.x = 0.0F;
      } else {
        this.x = var1;
      }

      if (var2 == 0.0F) {
        this.y = 0.0F;
      } else {
        this.y = var2;
      }

      if (var3 == 0.0F) {
        this.z = 0.0F;
      } else {
        this.z = var3;
      }

    }

    private void add(float var1, float var2, float var3) {
      this.x += var1;
      this.y += var2;
      this.z += var3;
    }

    private void normalize() {
      float var1 = (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
      if (var1 != 0.0F) {
        var1 = 1.0F / var1;
        this.x *= var1;
        this.y *= var1;
        this.z *= var1;
      }

    }

    public boolean equals(Object var1) {
      Normalizer.Triplef var2 = (Normalizer.Triplef)var1;
      return this.x == var2.x && this.y == var2.y && this.z == var2.z;
    }

    public int hashCode() {
      long var1 = 1L;
      var1 = 31L * var1 + (long)Float.floatToRawIntBits(this.x);
      var1 = 31L * var1 + (long)Float.floatToRawIntBits(this.y);
      var1 = 31L * var1 + (long)Float.floatToRawIntBits(this.z);
      return (int)(var1 ^ var1 >> 32);
    }

    public String toString() {
      return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
  }

  static final class Tuplef {
    final float x;
    final float y;

    Tuplef(float var1, float var2) {
      if (var1 == 0.0F) {
        this.x = 0.0F;
      } else {
        this.x = var1;
      }

      if (var2 == 0.0F) {
        this.y = 0.0F;
      } else {
        this.y = var2;
      }

    }

    public boolean equals(Object var1) {
      Normalizer.Tuplef var2 = (Normalizer.Tuplef)var1;
      return this.x == var2.x && this.y == var2.y;
    }

    public int hashCode() {
      long var1 = 1L;
      var1 = 31L * var1 + (long)Float.floatToRawIntBits(this.x);
      var1 = 31L * var1 + (long)Float.floatToRawIntBits(this.y);
      return (int)(var1 ^ var1 >> 32);
    }

    public String toString() {
      return "(" + this.x + ", " + this.y + ")";
    }
  }
}
