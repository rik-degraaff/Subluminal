package tech.subluminal.shared.stores;

import java.util.Optional;
import java.util.function.Function;
import tech.subluminal.shared.util.StoredSynchronized;
import tech.subluminal.shared.util.Synchronized;

public class SingleEntity<E> implements ReadOnlySingleEntity<E> {

  private Synchronized<Optional<E>> syncEntity = new StoredSynchronized<>(Optional.empty());

  /**
   * @return a synchonized optional containing the saved entity.
   */
  @Override
  public Synchronized<Optional<E>> get() {
    return syncEntity;
  }

  /**
   * @param entity the value to set the entity to.
   */
  public void set(E entity) {
    syncEntity.update(e -> Optional.of(entity));
  }

  /**
   * Removes the stored entity.
   */
  public void remove() {
    syncEntity.update(e -> Optional.empty());
  }

  public <R> ReadOnlySingleEntity<R> map(Function<E, R> mapper) {
    return () -> syncEntity.map(opt -> opt.map(mapper));
  }
}
