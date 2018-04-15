package tech.subluminal.client.stores;

import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.stores.IdentifiableCollection;
import tech.subluminal.shared.stores.records.game.Star;

/**
 * Keeps the current state of a game in a map of stars and a map of players.
 */
public class InMemoryGameStore implements GameStore {

  private final IdentifiableCollection<Star> stars = new IdentifiableCollection<>();
  private final IdentifiableCollection<Player> players = new IdentifiableCollection<>();


  @Override
  public IdentifiableCollection<Star> stars() {
    return stars;
  }

  @Override
  public IdentifiableCollection<Player> players() {
    return players;
  }
}
