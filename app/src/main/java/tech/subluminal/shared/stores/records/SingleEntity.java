package tech.subluminal.shared.stores.records;

import java.util.Optional;
import tech.subluminal.shared.util.Synchronized;

public class SingleEntity<E> {

  private Synchronized<Optional<E>> syncEntity = new Synchronized<>(Optional.empty());

  /**
   * @return a synchonized optional containing the saved entity.
   */
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
}
