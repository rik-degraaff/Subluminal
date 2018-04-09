package tech.subluminal.shared.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Adds the ability to create a stream to the Iterable inteface.
 *
 * @param <E> the element type of the Iterable.
 */
public interface Streamable<E> extends Iterable<E> {

  /**
   * @return a stream containing the elements in the iterable.
   */
  default Stream<E> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  /**
   * @return a possibly parallel stream containing the elements in the iterable.
   */
  default Stream<E> parallelStream() {
    return StreamSupport.stream(spliterator(), true);
  }
}
