package tech.subluminal.shared.son;

/**
 * An interface for java objects that can be represented as a SON object. Java does not allow
 * interfaces to declare static methods for child classes to implement, it is highly recommended
 * that you implement a `fromSON(SON)` static method when implementing this interface.
 */
public interface SONRepresentable {

  static SONConversionError error(String source, String key) {
    return new SONConversionError(source + "did not contain a valid " + key + ".");
  }
  // static Self fromSON(SON son) throws SONConversionError;

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  SON asSON();
}
