package tech.subluminal.client.stores;

import tech.subluminal.client.stores.records.game.OwnerPair;
import tech.subluminal.shared.stores.IdentifiableCollection;
import tech.subluminal.shared.stores.SingleEntity;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.stores.records.game.Star;

/**
 * Keeps the current state of a game in a map of stars and a map of players.
 */
public class InMemoryGameStore implements GameStore {

  private final IdentifiableCollection<Star> stars = new IdentifiableCollection<>();
  private final IdentifiableCollection<OwnerPair<Fleet>> fleets = new IdentifiableCollection<>();
  private final IdentifiableCollection<OwnerPair<Ship>> motherShips = new IdentifiableCollection<>();
  private final SingleEntity<Boolean> inGame = new SingleEntity<>();


  @Override
  public IdentifiableCollection<Star> stars() {
    return stars;
  }

  @Override
  public IdentifiableCollection<OwnerPair<Fleet>> fleets() {
    return fleets;
  }

  @Override
  public IdentifiableCollection<OwnerPair<Ship>> motherShips() {
    return motherShips;
  }

  @Override
  public SingleEntity<Boolean> inGame() {
    return inGame;
  }

}
