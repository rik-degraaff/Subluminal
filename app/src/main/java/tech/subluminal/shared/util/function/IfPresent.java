package tech.subluminal.shared.util.function;

import java.util.Optional;
import java.util.function.Consumer;

public interface IfPresent<E> extends IfPresentThen {

  static <E> IfPresent<E> ifPresent(Optional<E> opt) {
    return new IfPresentBase<>(opt);
  }

  IfPresentThen then(Consumer<? super E> action);

}
