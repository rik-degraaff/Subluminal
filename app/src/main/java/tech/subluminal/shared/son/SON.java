package tech.subluminal.shared.son;

import java.util.Optional;

/**
 * Subluminal Object Notation:
 *
 * A structured object with keys associated with typed values.
 */
public class SON {

  /**
   * Adds an integer value for a given key (or sequence of keys). Creates a hierarchy of SON objects
   * if necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(int value, String key, String... additionalKeys) {

    return this;
  }

  /**
   * Adds a double value for a given key (or sequence of keys). Creates a hierarchy of SON objects
   * if necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(double value, String key, String... additionalKeys) {

    return this;
  }

  /**
   * Adds a boolean value for a given key (or sequence of keys). Creates a hierarchy of SON objects
   * if necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(boolean value, String key, String... additionalKeys) {

    return this;
  }

  /**
   * Adds a string value for a given key (or sequence of keys). Creates a hierarchy of SON objects
   * if necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(String value, String key, String... additionalKeys) {

    return this;
  }

  /**
   * Adds a SON object for a given key (or sequence of keys). Creates a hierarchy of SON objects if
   * necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(SON value, String key, String... additionalKeys) {

    return this;
  }

  /**
   * Adds a SON list for a given key (or sequence of keys). Creates a hierarchy of SON objects if
   * necessary.
   *
   * @param value the value to add.
   * @param key the key to add the value to.
   * @param additionalKeys an optional sequence of keys to add a value into a nested structure.
   * @return this for method chaining
   */
  public SON put(SONList value, String key, String... additionalKeys) {

    return this;
  }

  /**
   * Gets an integer value for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Integer> getInt(String key, String... additionalKeys) {

    return Optional.empty();
  }

  /**
   * Gets a double value for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Double> getDouble(String key, String... additionalKeys) {

    return Optional.empty();
  }

  /**
   * Gets a boolean value for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Boolean> getBoolean(String key, String... additionalKeys) {

    return Optional.empty();
  }

  /**
   * Gets a string value for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<String> getString(String key, String... additionalKeys) {

    return Optional.empty();
  }

  /**
   * Gets a SON object for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<SON> getObject(String key, String... additionalKeys) {

    return Optional.empty();
  }

  /**
   * Gets a SON list for a given index.
   *
   * @param key the key to retrieve the value from
   * @param additionalKeys an optional sequence of keys to retrieve a value from a nested
   * structure.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<SONList> getList(String key, String... additionalKeys) {

    return Optional.empty();
  }
}
