package tech.subluminal.shared.util.function;

import java.util.Optional;
import java.util.function.Consumer;

public interface IfPresentThen {

  <R> IfPresentThen elseIfPresent(Optional<R> otherOpt, Consumer<? super R> action);

  void els(Runnable action);
}
