package tech.subluminal.shared.stores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.util.MapperList;
import tech.subluminal.shared.util.StoredSynchronized;
import tech.subluminal.shared.util.Synchronized;

public class IdentifiableCollection<E extends Identifiable> implements
    ReadOnlyIdentifiableCollection<E> {

  protected final Synchronized<ObservableMap<String, Synchronized<E>>> syncMap;

  private final ObservableList<String> observableList = FXCollections.observableArrayList();

  public IdentifiableCollection() {
    ObservableMap<String, Synchronized<E>> obsMap = FXCollections.observableHashMap();
    obsMap.addListener((MapChangeListener<String, Synchronized<E>>) change -> {
      String key = change.getKey();
      Platform.runLater(() -> {
        if (change.wasRemoved()) {
          observableList.remove(key);
        }
        if (change.wasAdded()) {
          observableList.add(key);
        }
      });
    });
    syncMap = new StoredSynchronized<>(obsMap);
  }

  /**
   * @return an observable list containing the entries in this collection.
   */
  @Override
  public ObservableList<E> observableList() {
    return new MapperList<>(observableList, key -> getByID(key).get().use(e -> e));
  }

  /**
   * @return a synchronized collection of synchronized entities which are stored in this collection.
   */
  @Override
  public Synchronized<Collection<Synchronized<E>>> getAll() {
    return syncMap.map(Map::values);
  }

  /**
   * Adds an entity to the collection.
   *
   * @param e the user to add.
   */
  public void add(E e) {
    syncMap.use(map -> map.put(e.getID(), new StoredSynchronized<>(e)));
  }

  /**
   * Returns an entry from the collection identified by a given id.
   *
   * @param id the id of the requested entity.
   * @return the requested item, empty if not found.
   */
  @Override
  public Optional<Synchronized<E>> getByID(String id) {
    return Optional.ofNullable(syncMap.use(map -> map.get(id)));
  }

  /**
   * Removes an entity identified by a given id from the collection.
   *
   * @param id the id of the entity that should be removed.
   */
  public void removeByID(String id) {
    syncMap.use(map -> map.remove(id));
  }

  /**
   * Executes an action in a synchronized block that guarantees that it cannot be interrupted by
   * another thread that also uses the collection.
   *
   * @param action the action to be performed.
   */
  public void sync(Runnable action) {
    syncMap.sync(action);
  }

  /**
   * Returns all entries from the collection that satisfy a given condition.
   *
   * @param predicate the condition the entries must satisfy
   * @return all entries that satify the condition.
   */
  protected Synchronized<Collection<Synchronized<E>>> getWithPredicate(Predicate<E> predicate) {
    return syncMap.map(map ->
        map.values().stream()
            .filter(syncUser -> syncUser.use(u -> predicate.test(u)))
            .collect(Collectors.toCollection(ArrayList::new))
    );
  }
}
