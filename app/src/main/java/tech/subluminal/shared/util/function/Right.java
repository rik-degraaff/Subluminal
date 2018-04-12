package tech.subluminal.shared.util.function;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An {@link Either} that can only contain a right value.
 */
public final class Right<L, R> extends Either<L, R> {

  private R value;

  public Right(R right) {
    value = right;
  }

  @Override
  public <T> T map(Function<? super L, ? extends T> leftMapper,
      Function<? super R, ? extends T> rightMapper) {
    return rightMapper.apply(value);
  }

  @Override
  public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> mapper) {
    return new Right<>(value);
  }

  @Override
  public <T> Either<L, T> mapRight(Function<? super R, ? extends T> mapper) {
    return new Right<>(mapper.apply(value));
  }

  @Override
  public void apply(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
    rightConsumer.accept(value);
  }
}
