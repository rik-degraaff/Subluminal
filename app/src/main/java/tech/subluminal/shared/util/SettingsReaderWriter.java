package tech.subluminal.shared.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.pmw.tinylog.Logger;
import tech.subluminal.shared.records.GlobalSettings;

public class SettingsReaderWriter implements PropertiesReaderWriter {
  public SettingsReaderWriter() {

  }

  @Override
  public void run(Class type, Object obj, String jarPath) {
    writeProperties(type, obj, jarPath);
    readProperties(type, obj, jarPath);
  }

  @Override
  public void readProperties(Class type, Object obj, String jarPath) {
    File f = new File(jarPath + "\\" + GlobalSettings.FILE_SETTINGS);

    if (f.exists() && !f.isDirectory()) {
      Properties props = new Properties();
      InputStream is = null;

      try {
        is = new FileInputStream(f);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      try {
        props.load(is);
      } catch (IOException e) {
        e.printStackTrace();
      }

      Stream<Field> fields = getFields(type, Export.class);
      fields.forEach(field -> {
        Object tmp = null;

        try {
          switch (field.getType().getSimpleName()) {
            case("int"): {
              tmp = Integer.parseInt(props.getProperty(field.getName()));
              break;
            }
            case("double"): {
              tmp = Double.parseDouble(props.getProperty(field.getName()));
              break;
            }
            case("long"): {
              tmp = Long.parseLong(props.getProperty(field.getName()));
              break;
            }
            case("boolean"): {
              tmp = Boolean.parseBoolean(props.getProperty(field.getName()));
              break;
            }
            case("String"): {
              tmp = props.getProperty(field.getName());
              break;
            }
            default: {
              Logger.error("No type handler found for: " + field.getType().getSimpleName());
              System.out.println("No type handler found: " + field.getType().getSimpleName());
              break;
            }
          }

          field.setAccessible(true);
          FieldUtils.removeFinalModifier(field, true);
          field.set(type, tmp);
          field.setAccessible(false);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      });

    } else {
      Logger.info("No settings found in location " + f.toString() + ". Nothing to load.");
      System.out.println("No settings found in location " + f.toString() + ". Nothing to load.");
    }
  }

  @Override
  public void writeProperties(Class type, Object obj, String jarPath) {
    File f = new File(jarPath + "\\" + GlobalSettings.FILE_SETTINGS);

    if (!f.exists() && !f.isDirectory()) {
      Properties props = new Properties();
      Stream<Field> fields = getFields(type, Export.class);
      fields.forEach(field -> {
            try {
              props.setProperty(field.getName(), field.get(obj).toString());
            } catch (IllegalAccessException e1) {
              e1.printStackTrace();
            }
          });

      try {
        OutputStream out = new FileOutputStream(f);
        props.store(out, "Subluminal settings file with @Export annotated fields");
      } catch (
          IOException e) {
        e.printStackTrace();
      }
    } else {
      Logger.info("Settings file (" + f.toString() + ") already exists. Won't overwrite.");
      System.out.println("Settings file (" + f.toString() + ") already exists. Won't overwrite.");
    }


  }

  @Override
  public List<Field> getFields(Class type) {
    List<Field> fields = new ArrayList<>();
    Collections.addAll(fields, type.getDeclaredFields());
    return fields;
  }

  @Override
  public Stream<Field> getFields(Class type, Class annotation) {
    return Arrays.stream(type.getFields())
        .filter(field -> field.isAnnotationPresent(annotation));
  }
}
