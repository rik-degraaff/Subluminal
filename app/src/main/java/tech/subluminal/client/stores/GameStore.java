package tech.subluminal.client.stores;

import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.stores.IdentifiableCollection;
import tech.subluminal.shared.stores.records.game.Star;

/**
 * The game store keeps the latest game state and is updated constantly.
 */
public interface GameStore {

  IdentifiableCollection<Star> stars();

  IdentifiableCollection<Player> players();

}
