package tech.subluminal.shared.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class StoredSynchronized<T> extends Synchronized<T> {
  private T value;

  public StoredSynchronized(T value) {
    this(value, new Object());
  }

  public StoredSynchronized(T value, Object lock) {
    super(lock);
    this.value = value;
  }

  @Override
  public void consume(Consumer<T> action) {
    synchronized (lock) {
      action.accept(value);
    }
  }

  @Override
  public <R> R use(Function<T, R> action) {
    synchronized (lock) {
      return action.apply(value);
    }
  }

  @Override
  public void update(Function<T, T> mapper) {
    synchronized (lock) {
      value = mapper.apply(value);
    }
  }
}
