package tech.subluminal.shared.son;

import java.util.Optional;

/**
 * Subluminal Object Notation List:
 *
 * A dynamic list with typed values.
 */
public class SONList {

  /**
   * Adds a value to the end of the list.
   *
   * @param value the integer value to add.
   * @return this for method chaining.
   */
  public SONList add(int value) {

    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the double value to add.
   * @return this for method chaining.
   */
  public SONList add(double value) {

    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the boolean value to add.
   * @return this for method chaining.
   */
  public SONList add(boolean value) {

    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the string value to add.
   * @return this for method chaining.
   */
  public SONList add(String value) {

    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the SON object to add.
   * @return this for method chaining.
   */
  public SONList add(SON value) {

    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the SON list value to add.
   * @return this for method chaining.
   */
  public SONList add(SONList value) {

    return this;
  }

  /**
   * Sets a value for a given index.
   *
   * @param value the integer value to set.
   * @param i the position where the value will be added.
   * @return this for method chaining.
   */
  public SONList set(int value, int i) {

    return this;
  }

  /**
   * Sets a value for a given index.
   *
   * @param value the double value to set.
   * @param i the position where the value will be added.
   * @return this for method chaining.
   */
  public SONList set(double value, int i) {

    return this;
  }

  /**
   * Sets a value for a given index.
   *
   * @param value the boolean value to set.
   * @param i the position where the value will be added.
   * @return this for method chaining.
   */
  public SONList set(boolean value, int i) {

    return this;
  }

  /**
   * Sets a value for a given index.
   *
   * @param value the string value to set.
   * @param i the position where the value will be added.
   * @return this for method chaining.
   */
  public SONList set(String value, int i) {

    return this;
  }

  /**
   * Sets a value for a given index.
   *
   * @param value the SON object to set.
   * @param i the position where the value will be added.
   * @return this for method chaining.
   */
  public SONList set(SON value, int i) {

    return this;
  }

  /**
   * Sets a value for a given index.
   *
   * @param value the SON list to set.
   * @param i the position where the value will be added.
   * @return this for method chaining.
   */
  public SONList set(SONList value, int i) {

    return this;
  }

  /**
   * Gets an integer for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Integer> getInt(int i) {

    return Optional.empty();
  }

  /**
   * Gets a double for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Double> getDouble(int i) {

    return Optional.empty();
  }

  /**
   * Gets a boolean for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Boolean> getBoolean(int i) {

    return Optional.empty();
  }

  /**
   * Gets a string for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<String> getString(int i) {

    return Optional.empty();
  }

  /**
   * Gets a SON object for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<SON> getObject(int i) {

    return Optional.empty();
  }

  /**
   * Gets a SON list for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<SONList> getList(int i) {

    return Optional.empty();
  }
}
