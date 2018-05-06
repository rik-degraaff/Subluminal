package tech.subluminal.shared.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for constants that are to be exported to a property file by {@link PropertiesReaderWriter}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Export {
  boolean exportToProperty() default true;
}
