package tech.subluminal.shared.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import org.pmw.tinylog.Logger;
import tech.subluminal.shared.records.GlobalSettings;

/**
 * Handles specific config and modding options and files. Can read, write and update property files
 * outside the jar.
 */
public class ConfigModifier<k, v> {

  private final String configPath;
  private String folderPath;
  private File attachedFile;
  public boolean isAttachedToFile;
  private ObservableMap<k, v> propMap = FXCollections.observableHashMap();
  private final String DELIMETER_KEY = "\\";

  public ConfigModifier(String path) {
    if (!GlobalSettings.PATH_JAR.equals("")) {
      configPath = GlobalSettings.PATH_JAR + DELIMETER_KEY + GlobalSettings.PATH_CONFIG;
      folderPath = configPath + DELIMETER_KEY + path.replace("/", DELIMETER_KEY);
      createDirectory(folderPath);
    } else {
     configPath = "config";
     folderPath = "config";
    }
  }

  /**
   * Reads a file from the given path if it exists. Otherwise it created the file.
   *
   * @param fullName filename or relative path with filename originating from the default location.
   * @return a new file reference.
   */
  public File getFile(String fullName) {
    File theFile = new File(folderPath + DELIMETER_KEY + fullName.replace("/", DELIMETER_KEY));
    createIfNotExists(theFile);
    return theFile;
  }

  private void createIfNotExists(File theFile) {
    if (!theFile.exists() && !theFile.isDirectory()) {
      theFile.getParentFile().mkdirs();
      try {
        theFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Reads all files from default location.
   * @return List of files found in directory.
   */
  public List<File> getAllFiles() {
    return getAllFiles("");
  }

  /**
   * Reads all files from default location found in specified subdirectory.
   *
   * @param fullName is a subdirectory of the default location.
   * @return List of files found in @param fullname.
   */
  public List<File> getAllFiles(String fullName) {
    File theFile = new File(folderPath + DELIMETER_KEY + fullName.replace("/", DELIMETER_KEY));
    File[] files;
    if (theFile.exists()) {
      files = theFile.listFiles();
    } else {
      files = new File[0];
    }
    List<File> temp = Arrays.asList(files);
    List<File> finalFiles = new LinkedList<>();
    temp.stream().filter(file -> !file.isDirectory()).forEach(file -> finalFiles.add(file));
    return finalFiles;
  }

  private void getProperties() {
    Properties props = new Properties();
    try {
      InputStream is = new FileInputStream(attachedFile);
      props.load(is);
      Set<Object> keys = props.keySet();
      keys.forEach(k -> propMap.put((k) k, (v) props.get(k)));

    } catch (FileNotFoundException e) {
      Logger.error(e);
    } catch (IOException e) {
      Logger.error(e);
    }
  }

  public File getAttachedFile() {
    return attachedFile;
  }

  public ObservableMap<k, v> getProps() {
    return propMap;
  }

  /**
   * Sets the attached properties file to the fullname relavtive to the default location and gets
   * all the keys and values.
   *
   * @param fullName filename or relative path with filename originating from the default location.
   */
  public void attachToFile(String fullName) {
    File theFile;
    if (GlobalSettings.PATH_JAR.equals("")) {
      theFile = new File(folderPath + DELIMETER_KEY + fullName.replace("/", DELIMETER_KEY));
    } else {
      theFile = new File(folderPath + DELIMETER_KEY + fullName.replace("/", DELIMETER_KEY));
    }
    System.out.println(theFile.getPath());
    String[] parts = fullName.split("\\.");

    switch (parts[parts.length-1]) {
      case ("properties"): {
        createIfNotExists(theFile);
        this.attachedFile = theFile;
        getProperties();
        propMap.addListener((MapChangeListener<k, v>) change -> {
          Properties props = new Properties();
          try {
            OutputStream out = new FileOutputStream(theFile);
            propMap.forEach((k,v) -> props.setProperty(k.toString(), v.toString()));
            props.store(out, "Subluminal Properties");
          } catch (FileNotFoundException e) {
            Logger.error(e);
          } catch (IOException e) {
            Logger.error(e);
          }
        });
        break;
      }
      case ("son"): {
        createIfNotExists(theFile);
        this.attachedFile = theFile;
        Logger.info("SON file attached.");
        break;
      }
      default: {
        Logger.info(parts[parts.length-1]);
        break;
      }
    }
    this.isAttachedToFile = true;
  }

  /**
   * Creates a all directories in a given path if it doesn't exist already.
   * @param path to be created.
   */
  private void createDirectory(String path) {
    File fo = new File(path);
    if (!fo.exists()) {
      fo.mkdirs();
    }
  }
}
