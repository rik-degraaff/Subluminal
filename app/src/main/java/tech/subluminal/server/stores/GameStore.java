package tech.subluminal.server.stores;

import tech.subluminal.server.stores.records.MoveRequests;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.shared.stores.IdentifiableCollection;

public interface GameStore {

  /**
   * @return a collection containing the game state of ongoing games.
   */
  IdentifiableCollection<GameState> games();

  /**
   * @return a collection containing move requests that were sent to games.
   */
  IdentifiableCollection<MoveRequests> gameSignals();
}
