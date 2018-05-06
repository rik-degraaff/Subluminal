package tech.subluminal.shared.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

public interface PropertiesReaderWriter {
  void run(Class type, Object obj, String path);
  void readProperties(Class type, Object obj, String path);
  void writeProperties(Class type, Object obj, String path);
  List<Field> getFields(Class type);
  Stream<Field> getFields(Class type, Class annotation);
}
