package tech.subluminal.shared.util.function;

import java.util.Comparator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FunctionalUtils {

  /**
   * If a value is present in the optional, performs the given action with the value, otherwise
   * performs the given empty-based action
   *
   * @param optional the optional on which the action is to be performed.
   * @param action the action to be performed, if a value is present.
   * @param emptyAction the empty-based action to be performed, if no value is present.
   */
  /*public static <T> void ifPresent(Optional<T> optional, Consumer<T> action, Runnable emptyAction) {
    if (optional.isPresent()) {
      action.accept(optional.get());
    } else {
      emptyAction.run();
    }
  }*/

  /**
   * This takeWhile function was copied from the java 9 source code and takes a Stream and returns
   *
   * @return a Stream containing all elements before the first element that doesn't satisfy the
   * predicate.
   */
  public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> p) {
    class Taking extends Spliterators.AbstractSpliterator<T> implements Consumer<T> {

      private static final int CANCEL_CHECK_COUNT = 63;
      private final Spliterator<T> s;
      private final AtomicBoolean cancel = new AtomicBoolean();
      private int count;
      private T t;
      private boolean takeOrDrop = true;

      Taking(Spliterator<T> s) {
        super(s.estimateSize(), s.characteristics() & ~(Spliterator.SIZED | Spliterator.SUBSIZED));
        this.s = s;
      }

      @Override
      public boolean tryAdvance(Consumer<? super T> action) {
        boolean test = true;
        if (takeOrDrop &&               // If can take
            (count != 0 || !cancel.get()) && // and if not cancelled
            s.tryAdvance(this) &&   // and if advanced one element
            (test = p.test(t))) {   // and test on element passes
          action.accept(t);           // then accept element
          return true;
        } else {
          // Taking is finished
          takeOrDrop = false;
          // Cancel all further traversal and splitting operations
          // only if test of element failed (short-circuited)
          if (!test) {
            cancel.set(true);
          }
          return false;
        }
      }

      @Override
      public Comparator<? super T> getComparator() {
        return s.getComparator();
      }

      @Override
      public void accept(T t) {
        count = (count + 1) & CANCEL_CHECK_COUNT;
        this.t = t;
      }

      @Override
      public Spliterator<T> trySplit() {
        return null;
      }
    }
    return StreamSupport.stream(new Taking(stream.spliterator()), stream.isParallel())
        .onClose(stream::close);
  }
}
