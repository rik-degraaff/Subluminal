package tech.subluminal.shared.util.function;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A class that contains a value of one of two types. This class cannot be extended or instatiated.
 *
 * @param <L> the type of the value if this Either is a {@link Left}.
 * @param <R> the type of the value if this Either is a {@link Left}.
 * @see Left
 * @see Right
 */
public abstract class Either<L, R> {

  Either() {
  }

  /**
   * Applies a transformation to the value inside this Either which maps both possible type to a
   * common type.
   *
   * @param leftMapper the mapper to apply if this either is a {@link Left}.
   * @param rightMapper the mapper to apply if this either is a {@link Right}.
   * @return the value returned by the mapper that was applied.
   */
  public abstract <T> T map(Function<? super L, ? extends T> leftMapper,
      Function<? super R, ? extends T> rightMapper);

  /**
   * Applies a mapper only if this Either is a {@link Left}.
   *
   * @param mapper the mapper to the left value if it is present.
   * @return the either with a mapped left value.
   */
  public abstract <T> Either<T, R> mapLeft(Function<? super L, ? extends T> mapper);

  /**
   * Applies a mapper only if this Either is a {@link Right}.
   *
   * @param mapper the mapper to the right value if it is present.
   * @return the either with a mapped right value.
   */
  public abstract <T> Either<L, T> mapRight(Function<? super R, ? extends T> mapper);

  /**
   * Consumes the value in the Either.
   *
   * @param leftConsumer the code that is called if this Either is a {@link Left}.
   * @param rightConsumer the code that is called if this Either is a {@link Right}.
   */
  public abstract void apply(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer);

  /**
   * @return the value if this Either is a {@link Left} or empty if it is a {@link Right}.
   */
  public Optional<L> left() {
    return map(Optional::ofNullable, r -> Optional.empty());
  }

  /**
   * @return the value if this Either is a {@link Right} or empty if it is a {@link Left}.
   */
  public Optional<R> right() {
    return map(l -> Optional.empty(), Optional::ofNullable);
  }
}
