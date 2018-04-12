package tech.subluminal.shared.util.function;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Either<L, R> {

  Either() {
  }

  public abstract <T> T map(Function<? super L, ? extends T> leftMapper,
      Function<? super R, ? extends T> rightMapper);

  public abstract <T> Either<T, R> mapLeft(Function<? super L, ? extends T> mapper);

  public abstract <T> Either<L, T> mapRight(Function<? super R, ? extends T> mapper);

  public abstract void apply(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer);

  public Optional<L> left() {
    return map(l -> Optional.ofNullable(l), r -> Optional.empty());
  }

  public Optional<R> right() {
    return map(l -> Optional.empty(), r -> Optional.ofNullable(r));
  }
}
