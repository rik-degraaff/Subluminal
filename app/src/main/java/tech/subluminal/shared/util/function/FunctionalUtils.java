package tech.subluminal.shared.util.function;

import java.util.Optional;
import java.util.function.Consumer;

public class FunctionalUtils {

  /**
   * If a value is present in the optional, performs the given action with the value, otherwise
   * performs the given empty-based action
   *
   * @param optional the optional on which the action is to be performed.
   * @param action the action to be performed, if a value is present.
   * @param emptyAction the empty-based action to be performed, if no value is present.
   */
  public static <T> void ifPresent(Optional<T> optional, Consumer<T> action, Runnable emptyAction) {
    if (optional.isPresent()) {
      action.accept(optional.get());
    } else {
      emptyAction.run();
    }
  }
}
