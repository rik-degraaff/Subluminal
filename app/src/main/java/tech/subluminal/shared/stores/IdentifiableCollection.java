package tech.subluminal.shared.stores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.util.Synchronized;

public class IdentifiableCollection<E extends Identifiable> implements
    ReadOnlyIdentifiableCollection<E> {

  private Synchronized<Map<String, Synchronized<E>>> syncMap = new Synchronized<>(new HashMap<>());

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
    syncMap.use(map -> map.put(e.getId(), new Synchronized<>(e)));
  }

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

  protected Synchronized<Collection<Synchronized<E>>> getWithPredicate(Predicate<E> predicate) {
    return syncMap.map(map ->
        map.values().stream()
          .filter(syncUser -> syncUser.use(u -> predicate.test(u)))
        .collect(Collectors.toCollection(ArrayList::new))
    );
  }
}
