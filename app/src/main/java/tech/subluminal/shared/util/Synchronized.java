package tech.subluminal.shared.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class Synchronized<T> {
  private T value;
  private final Object lock;

  public Synchronized(T value) {
    this(value, new Object());
  }

  public Synchronized(T value, Object lock) {
    this.value = value;
    this.lock = lock;
  }

  public void consume(Consumer<T> action) {
    synchronized (lock) {
      action.accept(value);
    }
  }

  public <R> R use(Function<T, R> action) {
    synchronized (lock) {
      return action.apply(value);
    }
  }

  public void update(Function<T, T> mapper) {
    synchronized (lock) {
      value = mapper.apply(value);
    }
  }

  public <R> Synchronized<R> map(Function<T, R> mapper) {
    synchronized (lock) {
      return new Synchronized<>(mapper.apply(value), lock);
    }
  }
}
