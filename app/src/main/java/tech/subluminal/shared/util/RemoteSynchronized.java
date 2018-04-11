package tech.subluminal.shared.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class RemoteSynchronized<T> extends Synchronized<T> {

  private final Supplier<T> getter;
  private final Consumer<T> setter;

  public RemoteSynchronized(Supplier<T> getter, Consumer<T> setter) {
    this(new Object(), getter, setter);
  }

  public RemoteSynchronized(Object lock, Supplier<T> getter, Consumer<T> setter) {
    super(lock);
    this.getter = getter;
    this.setter = setter;
  }

  @Override
  public void consume(Consumer<T> action) {
    synchronized (lock) {
      action.accept(getter.get());
    }
  }

  @Override
  public <R> R use(Function<T, R> action) {
    synchronized (lock) {
      return action.apply(getter.get());
    }
  }

  @Override
  public void update(Function<T, T> mapper) {
    synchronized (lock) {
      setter.accept(mapper.apply(getter.get()));
    }
  }
}
