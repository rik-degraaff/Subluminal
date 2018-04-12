package tech.subluminal.shared.util.function;

import java.util.function.Consumer;
import java.util.function.Function;

public final class Left<L, R> extends Either<L, R> {

  private L value;

  public Left(L left) {
    value = left;
  }

  @Override
  public <T> T map(Function<? super L, ? extends T> leftMapper,
      Function<? super R, ? extends T> rightMapper) {
    return leftMapper.apply(value);
  }

  @Override
  public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> mapper) {
    return new Left<>(mapper.apply(value));
  }

  @Override
  public <T> Either<L, T> mapRight(Function<? super R, ? extends T> mapper) {
    return new Left<>(value);
  }

  @Override
  public void apply(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
    leftConsumer.accept(value);
  }
}
