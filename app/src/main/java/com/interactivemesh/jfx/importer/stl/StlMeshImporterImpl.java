//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactivemesh.jfx.importer.stl;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.stl.MeshSmoother.SmoothMesh;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import javafx.scene.shape.TriangleMesh;

final class StlMeshImporterImpl {
  final String RELEASE = "0.6";
  private final EnumSet<StlImportOption> stlOpts = EnumSet.noneOf(StlImportOption.class);
  private double creaseAngle = Math.toRadians(45.0D);
  private int initCap = 4194304;
  private byte[] streamBytes = null;
  private ByteBuffer fChannelBuffer = null;
  private boolean isJavaRounding = true;
  private char[] asciiCharArray = null;
  private int coordsLength = 0;
  private int coordsSize = 0;
  private float[] coordsList = null;
  private int normalsLength = 0;
  private int normalsSize = 0;
  private float[] normalsList = null;
  private float[] coordsArray = null;
  private float[] normalsArray = null;
  private TriangleMesh importedMesh = null;
  private String solidName = null;
  private boolean isClosed = false;
  private final Indexer indexer;
  private final Normalizer normalizer;
  private final MeshSmoother smoother;
  private StlMeshImporter importer = null;

  StlMeshImporterImpl(StlMeshImporter var1) {
    this.importer = var1;
    this.indexer = new Indexer();
    this.normalizer = new Normalizer();
    this.smoother = new MeshSmoother();
    this.stlOpts.add(StlImportOption.NONE);
  }

  double getCreaseAngle() {
    return Math.toDegrees(this.creaseAngle);
  }

  void setCreaseAngle(double var1) {
    double var3 = Math.toRadians(var1);
    if (var3 < 0.0D) {
      var3 = 0.0D;
    } else if (var3 > 3.141592653589793D) {
      var3 = 3.1415927410125732D;
    }

    this.creaseAngle = var3;
  }

  EnumSet<StlImportOption> getOptions() {
    return this.stlOpts.clone();
  }

  void setOptions(StlImportOption... var1) {
    if (var1 != null && var1.length >= 1) {
      StlImportOption[] var2;
      int var3;
      int var4;
      StlImportOption var5;
      if (var1.length > 1) {
        var2 = var1;
        var3 = var1.length;

        for(var4 = 0; var4 < var3; ++var4) {
          var5 = var2[var4];
          if (var5 == StlImportOption.NONE) {
            throw new IllegalArgumentException("options include StlImportOption.NONE");
          }
        }
      }

      this.stlOpts.clear();
      var2 = var1;
      var3 = var1.length;

      for(var4 = 0; var4 < var3; ++var4) {
        var5 = var2[var4];
        this.stlOpts.add(var5);
      }

    } else {
      throw new IllegalArgumentException("options is null || options' length < 1");
    }
  }

  void setOptions(EnumSet<StlImportOption> var1) {
    if (var1 != null && !var1.isEmpty()) {
      if (var1.size() > 1) {
        Iterator var2 = var1.iterator();

        while(var2.hasNext()) {
          StlImportOption var3 = (StlImportOption)var2.next();
          if (var3 == StlImportOption.NONE) {
            throw new IllegalArgumentException("options include StlImportOption.NONE");
          }
        }
      }

      this.stlOpts.clear();
      this.stlOpts.addAll(var1);
    } else {
      throw new IllegalArgumentException("options is null || options is empty");
    }
  }

  void read(File var1) {
    if (!this.checkIsClosed()) {
      if (var1 == null) {
        throw new ImportException("StlMeshImporter read(File file) : file is null !");
      } else if (!var1.exists()) {
        throw new ImportException("StlMeshImporter read(File file) : file doesn't exist !");
      } else if (var1.isDirectory()) {
        throw new ImportException("StlMeshImporter read(File file) : file is directory !");
      } else if (!var1.canRead()) {
        throw new ImportException("StlMeshImporter read(File file) : file can't be read !");
      } else {
        try {
          FileChannel var2 = (new FileInputStream(var1)).getChannel();
          this.readFromChannel(var2);
        } catch (FileNotFoundException var3) {
          throw new ImportException("StlMeshImporter read(File file) : \n" + var3.getMessage(), var3);
        } catch (IOException var4) {
          throw new ImportException("StlMeshImporter read(File file) : \n" + var4.getMessage(), var4);
        }
      }
    }
  }

  void read(String var1) {
    if (!this.checkIsClosed()) {
      if (var1 != null && var1.length() >= 1) {
        URL var2 = null;

        try {
          var1 = var1.replace('\\', '/');
          var2 = this.getUrlForAbsolutPath(var1);
          if (var2 == null) {
            String var3 = System.getProperty("user.dir");
            if (var3.length() > 0) {
              var3 = var3.replace('\\', '/');
              if (var3.charAt(0) != '/') {
                var3 = '/' + var3;
              }

              if (var3.charAt(var3.length() - 1) != '/') {
                var3 = var3 + '/';
              }

              var2 = new URL("file", (String)null, this.normalizePath(var3, var1));
            }
          }

          if (var2 == null) {
            throw new ImportException("StlMeshImporter read(String fileName) : Can' create URL f0r = \n" + var1);
          }
        } catch (SecurityException var8) {
          throw new ImportException("StlMeshImporter read(String fileName) : \n" + var8.getMessage(), var8);
        } catch (MalformedURLException var9) {
          throw new ImportException("StlMeshImporter read(String fileName) : \n" + var9.getMessage(), var9);
        }

        try {
          if (var2.toExternalForm().indexOf("file") >= 0 && var2.toExternalForm().indexOf("jar:") < 0) {
            try {
              File var11 = new File(var2.toURI());
              if (var11 != null && var11.canRead()) {
                FileChannel var4 = (new FileInputStream(var11)).getChannel();
                this.readFromChannel(var4);
              }
            } catch (URISyntaxException var5) {
              throw new ImportException("StlMeshImporter read(String fileName) : \n" + var5.getMessage(), var5);
            } catch (FileNotFoundException var6) {
              throw new ImportException("StlMeshImporter read(String fileName) : \n" + var6.getMessage(), var6);
            }
          } else {
            URLConnection var10 = var2.openConnection();
            this.readFromStream(var10.getInputStream());
          }

        } catch (IOException var7) {
          throw new ImportException("StlMeshImporter read(String fileName) : \n" + var7.getMessage(), var7);
        }
      } else {
        throw new ImportException("StlMeshImporter read(String fileName) : fileName is null !");
      }
    }
  }

  void read(URL var1) {
    if (!this.checkIsClosed()) {
      if (var1 == null) {
        throw new ImportException("StlMeshImporter read(URL url) : url is null !");
      } else {
        try {
          if (var1.toExternalForm().indexOf("file") >= 0 && var1.toExternalForm().indexOf("jar:") < 0) {
            try {
              File var7 = new File(var1.toURI());
              if (var7 != null && var7.canRead()) {
                FileChannel var3 = (new FileInputStream(var7)).getChannel();
                this.readFromChannel(var3);
              }
            } catch (URISyntaxException var4) {
              throw new ImportException("StlMeshImporter read(URL url) : \n" + var4.getMessage(), var4);
            } catch (FileNotFoundException var5) {
              throw new ImportException("StlMeshImporter read(URL url) : \n" + var5.getMessage(), var5);
            }
          } else {
            URLConnection var2 = var1.openConnection();
            this.readFromStream(var2.getInputStream());
          }

        } catch (IOException var6) {
          throw new ImportException("StlMeshImporter read(URL url) : \n" + var6.getMessage(), var6);
        }
      }
    }
  }

  TriangleMesh getImport() {
    return this.importedMesh;
  }

  String getSolidName() {
    return this.solidName;
  }

  void clear() {
    this.coordsLength = 0;
    this.coordsSize = 0;
    this.coordsList = null;
    this.normalsLength = 0;
    this.normalsSize = 0;
    this.normalsList = null;
    this.coordsArray = null;
    this.normalsArray = null;
    this.importedMesh = null;
    this.solidName = null;
  }

  void close() {
    this.isClosed = true;
    this.clear();
    this.importer = null;
    this.indexer.close();
    this.smoother.close();
  }

  private boolean checkIsClosed() {
    if (this.isClosed) {
      throw new ImportException("StlMeshImporter is closed ! Can't be used anymore !");
    } else {
      return this.isClosed;
    }
  }

  private void createMesh() {
    Object var1 = null;
    Object var2 = null;
    Object var3 = null;
    Object var4 = null;
    float[] var5 = new float[]{0.0F, 0.0F};
    this.indexer.createRFTuples3fAndInds(this.coordsArray);
    int[] var20 = this.indexer.getIndices();
    float[] var19 = this.indexer.getIndexedVerts();
    int var6 = var20.length;
    int var7 = var19.length / 3;
    int var8 = var6 / 3;
    this.indexer.clear();
    int[] var21 = new int[var20.length];
    if (this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
      this.normalizer.calcIndexedVertexNormals(this.creaseAngle, (float[])null, var19, var7, var20, var21);
    } else {
      this.normalizer.calcIndexedVertexNormals(this.creaseAngle, this.normalsArray, (float[])null, var7, var20, var21);
    }

    int[] var9 = new int[var8];
    boolean var10 = false;
    int var12;
    int var28;
    if (this.creaseAngle >= 0.0D) {
      int var22 = 0;
      ArrayList var11 = this.smoother.create(var20, var19.length / 3, var21);
      var12 = var11.size();
      ArrayList var13 = null;
      boolean var14 = false;
      Iterator var15 = var11.iterator();

      while(var15.hasNext()) {
        SmoothMesh var16 = (SmoothMesh)var15.next();
        var13 = var16.getIndices();
        var28 = var16.getSmGrId();
        int var17 = 0;

        for(int var18 = var13.size(); var17 < var18; var17 += 3) {
          var9[(Integer)var13.get(var17) / 3] = var28;
        }

        var16.close();
        if (var28 > var22) {
          var22 = var28;
        }
      }

      var11.clear();
      this.smoother.clear();
    }

    int var27;
    if (this.stlOpts.contains(StlImportOption.REVERSE_GEOMETRY)) {
      byte var23 = 3;
      boolean var25 = true;
      var27 = var23 - 1;

      for(var28 = 0; var28 < var6; var28 += var23) {
        var12 = var20[var28 + var27];
        var20[var28 + var27] = var20[var28 + 1];
        var20[var28 + 1] = var12;
      }
    }

    int var24 = 0;

    for(var12 = var19.length; var24 < var12; var24 += 3) {
      var19[var24 + 1] = -var19[var24 + 1];
      var19[var24 + 2] = -var19[var24 + 2];
    }

    this.importedMesh = new TriangleMesh();
    this.importedMesh.getPoints().addAll(var19);
    this.importedMesh.getTexCoords().addAll(var5);
    int[] var26 = new int[var6 * 2];
    var12 = 0;

    for(var27 = 0; var27 < var6; ++var27) {
      var26[var12++] = var20[var27];
      var26[var12++] = 0;
    }

    this.importedMesh.getFaces().addAll(var26);
    this.importedMesh.getFaceSmoothingGroups().addAll(var9);
    this.importer.onFileImported();
  }

  private void readFromChannel(FileChannel var1) throws IOException {
    this.clear();
    byte[] var2 = new byte[84];
    ByteBuffer var3 = ByteBuffer.wrap(var2);
    var3.order(ByteOrder.nativeOrder());
    var1.read(var3);
    var3.clear();
    int var4 = -1;
    int var5 = -1;
    StringBuffer var6 = new StringBuffer();
    boolean var7 = false;

    for(int var8 = 0; var8 < 84; ++var8) {
      char var13 = (char)(var2[var8] & 255);
      if (var4 < 0 && var13 == '\n') {
        var4 = var8;
      }

      if (var5 < 0 && var13 == '\r') {
        var5 = var8;
      }

      var6.append(var13);
    }

    String var14 = var6.toString();
    int var9 = var14.indexOf("solid");
    boolean var10 = false;
    int var11;
    if (var9 >= 0 && (var4 > 0 || var5 > 0)) {
      var11 = var4;
      if (var4 < 0) {
        var11 = var5;
      }

      var14 = var14.substring(var9 + 5, var11).trim();
      this.solidName = var14;
      var10 = this.readAsciiChannel(var1, var3, var11 + 1);
    } else {
      var11 = 80;

      for(int var12 = 79; var12 >= 0; --var12) {
        if ((var2[var12] & 255) > 32) {
          var11 = var12 + 1;
          break;
        }
      }

      this.solidName = var14.substring(0, var11);
      var10 = this.readBinaryChannelFully(var1, var3);
    }

    if (var10) {
      this.createMesh();
    }

  }

  private void readFromStream(InputStream var1) throws IOException {
    this.clear();
    int var2 = var1.available();
    if (var2 >= 100) {
      int var3 = -1;
      int var4 = -1;
      byte[] var5 = new byte[84];
      var1.read(var5, 0, 84);
      StringBuffer var6 = new StringBuffer();
      boolean var7 = false;

      for(int var8 = 0; var8 < 84; ++var8) {
        char var13 = (char)(var5[var8] & 255);
        if (var3 < 0 && var13 == '\n') {
          var3 = var8;
        }

        if (var4 < 0 && var13 == '\r') {
          var4 = var8;
        }

        var6.append(var13);
      }

      String var14 = var6.toString();
      int var9 = var14.indexOf("solid");
      boolean var10 = false;
      int var11;
      if (var9 < 0 || var3 <= 0 && var4 <= 0) {
        var11 = 80;

        for(int var12 = 79; var12 >= 0; --var12) {
          if ((var5[var12] & 255) > 32) {
            var11 = var12 + 1;
            break;
          }
        }

        this.solidName = var14.substring(0, var11);
        var10 = this.readBinaryStreamFully(var1, var2, var5);
      } else {
        var11 = var3;
        if (var3 < 0) {
          var11 = var4;
        }

        var14 = var14.substring(var9 + 5, var11).trim();
        this.solidName = var14;
        var10 = this.readAsciiStream(var1, var2, var5, var11 + 1);
      }

      if (var10) {
        this.createMesh();
      }

    }
  }

  private boolean readAsciiChannel(FileChannel var1, ByteBuffer var2, int var3) throws IOException {
    int var5 = (int)((var1.size() + 4096L) / 208L);
    this.coordsLength = var5 * 9;
    this.normalsLength = var5 * 3;
    this.coordsList = new float[this.coordsLength];
    if (!this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
      this.normalsList = new float[this.normalsLength];
    }

    if (this.fChannelBuffer == null) {
      this.fChannelBuffer = ByteBuffer.allocateDirect(this.initCap).order(ByteOrder.nativeOrder());
      if (this.isJavaRounding) {
        this.asciiCharArray = new char[this.initCap];
      }
    }

    this.fChannelBuffer.clear();
    var2.position(var3);
    this.fChannelBuffer.put(var2);
    int var6 = this.fChannelBuffer.position();
    boolean var7 = false;
    boolean var8 = false;
    boolean var9 = true;

    while(true) {
      while(var9) {
        this.fChannelBuffer.position(var6);
        this.fChannelBuffer.limit(this.fChannelBuffer.capacity());
        int var15 = var1.read(this.fChannelBuffer);
        if (var15 < 0) {
          var9 = false;
        } else if (var15 > 0) {
          int var14 = this.fChannelBuffer.position();
          this.fChannelBuffer.position(0);
          int var10 = -1;
          int var11;
          if (this.isJavaRounding) {
            for(var11 = 0; var11 < var14; ++var11) {
              this.asciiCharArray[var11] = (char)(this.fChannelBuffer.get() & 255);
            }

            var10 = this.readFacets(new String(this.asciiCharArray, 0, var14), this.asciiCharArray, 0, var14);
          }

          if (var10 < 0) {
            var9 = false;
          } else {
            var11 = var14 - var10;
            if (var11 <= 0) {
              var6 = 0;
            } else {
              int var12 = 0;

              for(int var13 = 0; var13 < var11; ++var13) {
                this.fChannelBuffer.put(var12++, this.fChannelBuffer.get(var10 + var13));
              }

              var6 = var11;
            }
          }
        }
      }

      var1.close();
      if (!this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
        this.normalsArray = new float[this.normalsSize];
        System.arraycopy(this.normalsList, 0, this.normalsArray, 0, this.normalsSize);
      }

      this.coordsArray = new float[this.coordsSize];
      System.arraycopy(this.coordsList, 0, this.coordsArray, 0, this.coordsSize);
      this.normalsList = null;
      this.coordsList = null;
      return true;
    }
  }

  private boolean readAsciiStream(InputStream var1, int var2, byte[] var3, int var4) throws IOException {
    int var6 = (var2 + 4096) / 208;
    this.coordsLength = var6 * 9;
    this.normalsLength = var6 * 3;
    this.coordsList = new float[this.coordsLength];
    if (!this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
      this.normalsList = new float[this.normalsLength];
    }

    if (this.streamBytes == null) {
      this.streamBytes = new byte[this.initCap];
      if (this.isJavaRounding) {
        this.asciiCharArray = new char[this.initCap];
      }
    }

    int var7 = 0;

    int var8;
    for(var8 = var4; var8 < var3.length; ++var8) {
      this.streamBytes[var7++] = var3[var8];
    }

    var8 = var7;
    int var9 = this.streamBytes.length - var7;
    boolean var10 = false;
    boolean var11 = false;
    boolean var12 = true;

    while(true) {
      while(var12) {
        int var17 = var1.read(this.streamBytes, var8, var9);
        if (var17 < 0) {
          var12 = false;
        } else if (var17 > 0) {
          int var16 = var8 + var17;
          int var13 = -1;
          int var14;
          if (this.isJavaRounding) {
            for(var14 = 0; var14 < var16; ++var14) {
              this.asciiCharArray[var14] = (char)(this.streamBytes[var14] & 255);
            }

            var13 = this.readFacets(new String(this.asciiCharArray, 0, var16), this.asciiCharArray, 0, var16);
          }

          if (var13 < 0) {
            var12 = false;
          } else {
            var14 = var16 - var13;
            if (var14 > 0) {
              var7 = 0;

              for(int var15 = 0; var15 < var14; ++var15) {
                this.streamBytes[var7++] = this.streamBytes[var13 + var15];
              }

              var8 = var14;
            } else {
              var8 = 0;
            }

            var9 = this.streamBytes.length - var8;
          }
        }
      }

      var1.close();
      if (!this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
        this.normalsArray = new float[this.normalsSize];
        System.arraycopy(this.normalsList, 0, this.normalsArray, 0, this.normalsSize);
      }

      this.coordsArray = new float[this.coordsSize];
      System.arraycopy(this.coordsList, 0, this.coordsArray, 0, this.coordsSize);
      this.normalsList = null;
      this.coordsList = null;
      return true;
    }
  }

  private int readFacets(String var1, char[] var2, int var3, int var4) {
    int var5 = var3;
    float[] var6 = new float[3];
    float[] var7 = new float[3];
    float[] var8 = new float[3];
    float[] var9 = new float[3];
    int var10 = var3 - 1;
    var10 += 8;

    while(true) {
      label95:
      while(true) {
        do {
          ++var10;
          if (var10 >= var4) {
            return var5;
          }
        } while(var2[var10] != 'l');

        if (var2[var10 - 1] == 'o') {
          return -1;
        }

        if (!this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL) && (var10 = this.readThreeFloatsJava(var1, var2, var10, var4, var6)) < 0) {
          return var5;
        }

        var10 += 15;

        label93:
        while(true) {
          do {
            ++var10;
            if (var10 >= var4) {
              continue label95;
            }
          } while(var2[var10] != 'x');

          if ((var10 = this.readThreeFloatsJava(var1, var2, var10, var4, var7)) < 0) {
            return var5;
          }

          var10 += 5;

          label91:
          while(true) {
            do {
              ++var10;
              if (var10 >= var4) {
                continue label93;
              }
            } while(var2[var10] != 'x');

            if ((var10 = this.readThreeFloatsJava(var1, var2, var10, var4, var8)) < 0) {
              return var5;
            }

            var10 += 5;

            label89:
            while(true) {
              do {
                ++var10;
                if (var10 >= var4) {
                  continue label91;
                }
              } while(var2[var10] != 'x');

              if ((var10 = this.readThreeFloatsJava(var1, var2, var10, var4, var9)) < 0) {
                return var5;
              }

              var10 += 13;

              do {
                ++var10;
                if (var10 >= var4) {
                  continue label89;
                }
              } while(var2[var10] != 't');

              var5 = var10 + 1;
              float[] var11;
              if (!this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
                if (this.normalsSize > this.normalsLength - 3) {
                  var11 = this.normalsList;
                  this.normalsLength = (int)((double)this.normalsSize * 1.2D);
                  this.normalsList = new float[this.normalsLength];
                  System.arraycopy(var11, 0, this.normalsList, 0, var11.length);
                }

                this.normalsList[this.normalsSize++] = var6[0];
                this.normalsList[this.normalsSize++] = var6[1];
                this.normalsList[this.normalsSize++] = var6[2];
              }

              if (this.coordsSize > this.coordsLength - 9) {
                var11 = this.coordsList;
                this.coordsLength = (int)((double)this.coordsSize * 1.2D);
                this.coordsList = new float[this.coordsLength];
                System.arraycopy(var11, 0, this.coordsList, 0, var11.length);
              }

              this.coordsList[this.coordsSize++] = var7[0];
              this.coordsList[this.coordsSize++] = var7[1];
              this.coordsList[this.coordsSize++] = var7[2];
              this.coordsList[this.coordsSize++] = var8[0];
              this.coordsList[this.coordsSize++] = var8[1];
              this.coordsList[this.coordsSize++] = var8[2];
              this.coordsList[this.coordsSize++] = var9[0];
              this.coordsList[this.coordsSize++] = var9[1];
              this.coordsList[this.coordsSize++] = var9[2];
              continue label95;
            }
          }
        }
      }
    }
  }

  private int readThreeFloatsJava(String var1, char[] var2, int var3, int var4, float[] var5) {
    byte var6 = 32;
    boolean var7 = false;
    boolean var8 = false;
    int var9 = var3;
    int var10 = 0;

    try {
      label29:
      while(true) {
        ++var9;
        if (var9 >= var4) {
          break;
        }

        if (var2[var9] > var6) {
          int var13 = var9;

          do {
            ++var9;
            if (var9 >= var4) {
              continue label29;
            }
          } while(var2[var9] > var6);

          var5[var10++] = Float.parseFloat(var1.substring(var13, var9));
          if (var10 == 3) {
            return var9;
          }
        }
      }
    } catch (NumberFormatException var12) {
      var12.printStackTrace();
    }

    return -1;
  }

  private boolean readBinaryChannelFully(FileChannel var1, ByteBuffer var2) throws IOException {
    var2.position(0);
    byte[] var3 = new byte[80];
    var2.get(var3);
    new String(var3);
    int var5 = (int)((long)var2.getInt() & 4294967295L);
    if (var5 < 1) {
      var1.close();
      return false;
    } else {
      if (!this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
        this.normalsArray = new float[var5 * 3];
      }

      this.coordsArray = new float[var5 * 9];
      int var6 = var5 * 50;
      ByteBuffer var7 = ByteBuffer.allocateDirect(var6).order(ByteOrder.LITTLE_ENDIAN);
      var7.clear();
      int var8 = var6;
      boolean var9 = false;

      int var13;
      do {
        var13 = var1.read(var7);
        var8 -= var13;
      } while(var13 >= 0 && var8 > 0);

      if (var8 > 0) {
        var1.close();
        throw new ImportException("Can't read all bytes from channel : still to read =" + var8 + " from total " + var6);
      } else {
        var7.position(0);
        int var10 = 0;
        int var11 = 0;

        for(int var12 = 0; var12 < var5; ++var12) {
          if (this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
            var7.position(var7.position() + 12);
          } else {
            this.normalsArray[var10++] = var7.getFloat();
            this.normalsArray[var10++] = var7.getFloat();
            this.normalsArray[var10++] = var7.getFloat();
          }

          this.coordsArray[var11++] = var7.getFloat();
          this.coordsArray[var11++] = var7.getFloat();
          this.coordsArray[var11++] = var7.getFloat();
          this.coordsArray[var11++] = var7.getFloat();
          this.coordsArray[var11++] = var7.getFloat();
          this.coordsArray[var11++] = var7.getFloat();
          this.coordsArray[var11++] = var7.getFloat();
          this.coordsArray[var11++] = var7.getFloat();
          this.coordsArray[var11++] = var7.getFloat();
          var7.getShort();
        }

        var1.close();
        return true;
      }
    }
  }

  private boolean readBinaryStreamFully(InputStream var1, int var2, byte[] var3) throws IOException {
    ByteBuffer var4 = ByteBuffer.wrap(var3).order(ByteOrder.LITTLE_ENDIAN);
    var4.clear();
    new String(var3, 0, 80);
    var4.position(80);
    int var6 = (int)((long)var4.getInt() & 4294967295L);
    if (var6 < 1) {
      var1.close();
      return false;
    } else {
      if (!this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
        this.normalsArray = new float[var6 * 3];
      }

      this.coordsArray = new float[var6 * 9];
      int var7 = var6 * 50;
      byte[] var8 = new byte[var7];
      ByteBuffer var9 = ByteBuffer.wrap(var8).order(ByteOrder.LITTLE_ENDIAN);
      int var10 = 0;
      int var11 = var7;
      boolean var12 = false;

      int var17;
      do {
        var17 = var1.read(var8, var10, var11);
        var10 += var17;
        var11 -= var17;
      } while(var17 >= 0 && var11 > 0);

      if (var11 > 0) {
        var1.close();
        throw new ImportException("Can't read all bytes from stream : still to read =" + var11 + " from total " + var7);
      } else {
        var9.limit(var7);
        var9.position(0);
        boolean var13 = false;
        int var14 = 0;
        int var15 = 0;

        for(int var16 = 0; var16 < var6; ++var16) {
          if (this.stlOpts.contains(StlImportOption.CALCULATE_FACET_NORMAL)) {
            var9.position(var9.position() + 12);
          } else {
            this.normalsArray[var14++] = var9.getFloat();
            this.normalsArray[var14++] = var9.getFloat();
            this.normalsArray[var14++] = var9.getFloat();
          }

          this.coordsArray[var15++] = var9.getFloat();
          this.coordsArray[var15++] = var9.getFloat();
          this.coordsArray[var15++] = var9.getFloat();
          this.coordsArray[var15++] = var9.getFloat();
          this.coordsArray[var15++] = var9.getFloat();
          this.coordsArray[var15++] = var9.getFloat();
          this.coordsArray[var15++] = var9.getFloat();
          this.coordsArray[var15++] = var9.getFloat();
          this.coordsArray[var15++] = var9.getFloat();
          var9.getShort();
        }

        var1.close();
        return true;
      }
    }
  }

  private URL getUrlForAbsolutPath(String var1) {
    URL var2 = null;
    int var3 = var1.indexOf(":");

    try {
      if (var3 > 0) {
        String var4 = var1.substring(0, var3);
        String var5 = var4.toLowerCase(Locale.ENGLISH);
        URI var6;
        if (!var5.startsWith("jar") && !var5.startsWith("http") && !var5.startsWith("file") && !var5.startsWith("ftp")) {
          if (var1.charAt(0) != '/') {
            var2 = new URL("file", (String)null, "/" + var1);
          } else {
            var6 = new URI(var1);
            var2 = var6.toURL();
          }
        } else {
          var6 = new URI(var1);
          var2 = var6.toURL();
        }
      } else if (var1.charAt(0) == '/') {
        var2 = new URL("file", (String)null, var1);
      }

      return var2;
    } catch (URISyntaxException var7) {
      throw new ImportException("StlMeshImporter : Can't create URI !\nPath = " + var1 + "<!", var7);
    } catch (MalformedURLException var8) {
      throw new ImportException("StlMeshImporter : Can't create URL !\nPath = " + var1 + "<!", var8);
    }
  }

  private String normalizePath(String var1, String var2) {
    if (var2.indexOf("./") == 0) {
      var2 = var2.substring(2);
      return new String(var1 + var2);
    } else {
      boolean var3 = true;
      int var4 = var2.lastIndexOf("../");
      int var5 = 0;
      if (var4 >= 0) {
        var5 = var4 / 3 + 1;
      }

      if (var5 > 0) {
        int var6 = var1.length() - 2;
        int var7 = var1.lastIndexOf(47, var6);
        if (var7 < 0) {
          throw new ImportException("StlMeshImporter : normalizePath : lastBaseIndex < 0 ! \nbase = " + var1 + " child = " + var2);
        } else {
          --var5;

          while(var5 > 0) {
            var7 = var1.lastIndexOf(47, var7 - 1);
            if (var7 < 0) {
              throw new ImportException("StlMeshImporter : normalizePath : lastBaseIndex < 0 ! \nbase = " + var1 + " child = " + var2);
            }

            --var5;
          }

          return new String(var1.substring(0, var7 + 1) + var2.substring(var4 + 3));
        }
      } else {
        return new String(var1 + var2);
      }
    }
  }
}
