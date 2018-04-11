package tech.subluminal.shared.util;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Synchronized<T> {

  protected final Object lock;

  protected Synchronized(Object lock) {
    this.lock = lock;
  }

  public abstract void consume(Consumer<T> action);

  public abstract <R> R use(Function<T, R> action);

  public abstract void update(Function<T, T> mapper);

  public <R> Synchronized<R> map(Function<T, R> mapper) {
    return new RemoteSynchronized<>(lock,
        () -> mapper.apply(use(e -> e)),
        n -> {
        });
  }

  public <R> Synchronized<R> map(Function<T, R> mapper, Function<R, T> reverseMapper) {
    return new RemoteSynchronized<>(lock,
        () -> mapper.apply(use(e -> e)),
        n -> update(e -> reverseMapper.apply(n)));
  }

  public void sync(Runnable action) {
    synchronized (lock) {
      action.run();
    }
  }
}
