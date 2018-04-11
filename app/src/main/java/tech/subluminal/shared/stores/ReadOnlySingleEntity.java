package tech.subluminal.shared.stores;

import java.util.Optional;
import tech.subluminal.shared.util.Synchronized;

public interface ReadOnlySingleEntity<E> {

  /**
   * @return a synchonized optional containing the saved entity.
   */
  Synchronized<Optional<E>> get();
}
