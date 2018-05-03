package tech.subluminal.shared.util.function;

import java.util.Optional;
import java.util.function.Consumer;

import static tech.subluminal.shared.util.function.IfPresent.ifPresent;

class IfPresentBase<E> implements IfPresent<E> {
  final Optional<E> opt;

  IfPresentBase(Optional<E> opt) {
    this.opt = Optional.ofNullable(opt.orElse(null));
  }

  @Override
  public IfPresentThen then(Consumer<? super E> action) {
    opt.ifPresent(action);
    return this;
  }

  @Override
  public <R> IfPresentThen elseIfPresent(Optional<R> otherOpt, Consumer<? super R> action) {
    return opt.isPresent() ? ifPresent(otherOpt) : ifPresent(otherOpt).then(action);
  }

  @Override
  public void els(Runnable action) {
    if (!opt.isPresent()) {
      action.run();
    }
  }
}
