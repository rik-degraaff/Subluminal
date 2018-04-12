package tech.subluminal.shared.stores;

import java.util.Collection;
import java.util.Optional;
import javafx.collections.ObservableList;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.util.Synchronized;

public interface ReadOnlyIdentifiableCollection<E extends Identifiable> {

  /**
   * @return an observable list containing the entries in this collection.
   */
  ObservableList<E> observableList();

  /**
   * @return all the entities that are saved in this collection.
   */
  Synchronized<Collection<Synchronized<E>>> getAll();

  /**
   * Retrieves an entity identified by a given id.
   *
   * @param id the id of the requested entity.
   * @return the entity, empty when not found.
   */
  Optional<Synchronized<E>> getByID(String id);
}
