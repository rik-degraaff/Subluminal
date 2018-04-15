package tech.subluminal.shared.son;

import static tech.subluminal.shared.son.SONParsing.BOOLEAN_ID;
import static tech.subluminal.shared.son.SONParsing.DOUBLE_ID;
import static tech.subluminal.shared.son.SONParsing.ENTRY_DELIMITER;
import static tech.subluminal.shared.son.SONParsing.INTEGER_ID;
import static tech.subluminal.shared.son.SONParsing.LIST_ID;
import static tech.subluminal.shared.son.SONParsing.LIST_LEFTBRACE;
import static tech.subluminal.shared.son.SONParsing.LIST_RIGHTBRACE;
import static tech.subluminal.shared.son.SONParsing.OBJECT_ID;
import static tech.subluminal.shared.son.SONParsing.STRING_ID;
import static tech.subluminal.shared.son.SONParsing.booleanString;
import static tech.subluminal.shared.son.SONParsing.doubleString;
import static tech.subluminal.shared.son.SONParsing.integerString;
import static tech.subluminal.shared.son.SONParsing.partialParseBoolean;
import static tech.subluminal.shared.son.SONParsing.partialParseDouble;
import static tech.subluminal.shared.son.SONParsing.partialParseInt;
import static tech.subluminal.shared.son.SONParsing.partialParseString;
import static tech.subluminal.shared.son.SONParsing.stringString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Stream;
import tech.subluminal.shared.son.SONParsing.PartialParseResult;
import tech.subluminal.shared.util.Streamable;

/**
 * Subluminal Object Notation List:
 *
 * <p>A dynamic list with typed values.
 */
public class SONList {

  private List<Object> list = new ArrayList<>();

  static PartialParseResult<SONList> partialParse(String str, int start)
      throws SONParsingError {
    try {
      if (str.charAt(start) != LIST_LEFTBRACE) {
        throw new SONParsingError("Expected a list but found no left brace.");
      }

      SONList list = new SONList();

      if (str.charAt(start + 1) == LIST_RIGHTBRACE) {
        return new PartialParseResult<>(list, start + 2);
      }
      int i = start;

      do {
        i++;
        char typeID = str.charAt(i++);
        switch (typeID) {
          case INTEGER_ID:
            PartialParseResult<Integer> intRes = partialParseInt(str, i);
            i = intRes.pos;
            list.add(intRes.result);
            break;
          case DOUBLE_ID:
            PartialParseResult<Double> doubleRes = partialParseDouble(str, i);
            i = doubleRes.pos;
            list.add(doubleRes.result);
            break;
          case BOOLEAN_ID:
            PartialParseResult<Boolean> boolRes = partialParseBoolean(str, i);
            i = boolRes.pos;
            list.add(boolRes.result);
            break;
          case STRING_ID:
            PartialParseResult<String> strRes = partialParseString(str, i);
            i = strRes.pos;
            list.add(strRes.result);
            break;
          case OBJECT_ID:
            PartialParseResult<SON> objRes = SON.partialParse(str, i);
            i = objRes.pos;
            list.add(objRes.result);
            break;
          case LIST_ID:
            PartialParseResult<SONList> listRes = SONList.partialParse(str, i);
            i = listRes.pos;
            list.add(listRes.result);
            break;
          default:
            throw new SONParsingError(
                "Expected a value, but found no type identifier. Instead found: '" + typeID + "'");
        }
      } while (str.charAt(i) == ENTRY_DELIMITER);

      if (str.charAt(i) != LIST_RIGHTBRACE) {
        throw new SONParsingError("SON list was not terminated.");
      }

      return new PartialParseResult<>(list, i + 1);
    } catch (IndexOutOfBoundsException e) {
      throw new SONParsingError("SON list was not terminated.");
    }
  }

  /**
   * Returns the number of elements this SONList contains.
   *
   * @return the number of elements in this SONList.
   */
  public int size() {
    return list.size();
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the integer value to add.
   * @return this for method chaining.
   */
  public SONList add(int value) {
    list.add(value);
    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the double value to add.
   * @return this for method chaining.
   */
  public SONList add(double value) {
    list.add(value);
    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the boolean value to add.
   * @return this for method chaining.
   */
  public SONList add(boolean value) {
    list.add(value);
    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the string value to add.
   * @return this for method chaining.
   */
  public SONList add(String value) {
    list.add(value);
    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the SON object to add.
   * @return this for method chaining.
   */
  public SONList add(SON value) {
    list.add(value);
    return this;
  }

  /**
   * Adds a value to the end of the list.
   *
   * @param value the SON list value to add.
   * @return this for method chaining.
   */
  public SONList add(SONList value) {
    list.add(value);
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
    list.set(i, value);
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
    list.set(i, value);
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
    list.set(i, value);
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
    list.set(i, value);
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
    list.set(i, value);
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
    list.set(i, value);
    return this;
  }

  /**
   * Gets an integer for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Integer> getInt(int i) {
    Object value = list.get(i);
    return value instanceof Integer ? Optional.of((Integer) value) : Optional.empty();
  }

  /**
   * Gets a double for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Double> getDouble(int i) {
    Object value = list.get(i);
    return value instanceof Double ? Optional.of((Double) value) : Optional.empty();
  }

  /**
   * Gets a boolean for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<Boolean> getBoolean(int i) {
    Object value = list.get(i);
    return value instanceof Boolean ? Optional.of((Boolean) value) : Optional.empty();
  }

  /**
   * Gets a string for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<String> getString(int i) {
    Object value = list.get(i);
    return value instanceof String ? Optional.of((String) value) : Optional.empty();
  }

  /**
   * Gets a SON object for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<SON> getObject(int i) {
    Object value = list.get(i);
    return value instanceof SON ? Optional.of((SON) value) : Optional.empty();
  }

  /**
   * Gets a SON list for a given index.
   *
   * @param i the index to look at.
   * @return the found value if it was found and the right type, empty otherwise.
   */
  public Optional<SONList> getList(int i) {
    Object value = list.get(i);
    return value instanceof SONList ? Optional.of((SONList) value) : Optional.empty();
  }

  public String asString() {
    StringBuilder builder = new StringBuilder();

    builder.append(LIST_LEFTBRACE);

    list.stream()
        .map(v -> {
          if (v instanceof Integer) {
            return INTEGER_ID + integerString((Integer) v);
          } else if (v instanceof Double) {
            return DOUBLE_ID + doubleString((Double) v);
          } else if (v instanceof Boolean) {
            return BOOLEAN_ID + booleanString((Boolean) v);
          } else if (v instanceof String) {
            return STRING_ID + stringString((String) v);
          } else if (v instanceof SON) {
            return OBJECT_ID + ((SON) v).asString();
          } else if (v instanceof SONList) {
            return LIST_ID + ((SONList) v).asString();
          }
          throw new RuntimeException(new SONParsingError(
              "Encountered an unexpected type while printing a SONList: " + v.toString()));
        })
        .flatMap(s -> Stream.of(ENTRY_DELIMITER, s))
        .skip(1)
        .forEach(builder::append);

    builder.append(LIST_RIGHTBRACE);

    return builder.toString();
  }

  /**
   * @return a streamable containing all the integers in this list in order.
   */
  public Streamable<Integer> ints() {
    return streamable(this::getInt);
  }

  /**
   * @return a streamable containing all the doubles in this list in order.
   */
  public Streamable<Double> doubles() {
    return streamable(this::getDouble);
  }

  /**
   * @return a streamable containing all the booleans in this list in order.
   */
  public Streamable<Boolean> booleans() {
    return streamable(this::getBoolean);
  }

  /**
   * @return a streamable containing all the strings in this list in order.
   */
  public Streamable<String> strings() {
    return streamable(this::getString);
  }

  /**
   * @return a streamable containing all the objects in this list in order.
   */
  public Streamable<SON> objects() {
    return streamable(this::getObject);
  }

  /**
   * @return a streamable containing all the lists in this list in order.
   */
  public Streamable<SONList> lists() {
    return streamable(this::getList);
  }

  private <E> Streamable<E> streamable(IntFunction<Optional<E>> getter) {
    return () -> new Iterator<E>() {
      int i = 0;

      @Override
      public boolean hasNext() {
        if (i >= size()) {
          return false;
        }

        while (!getter.apply(i).isPresent()) {
          i++;
          if (i >= size()) {
            return false;
          }
        }
        return true;
      }

      @Override
      public E next() {
        while (!getter.apply(i).isPresent()) {
          i++;
        }
        return getter.apply(i++).get();
      }
    };
  }
}
