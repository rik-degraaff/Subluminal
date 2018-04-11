package tech.subluminal.shared.stores;

import java.util.function.Function;
import tech.subluminal.shared.util.Synchronized;

import java.util.Optional;

public interface ReadOnlySingleEntity<E> {

    /**
     * @return a synchonized optional containing the saved entity.
     */
    Synchronized<Optional<E>> get();
}
