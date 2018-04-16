package tech.subluminal.server.stores;

import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.MoveRequests;
import tech.subluminal.shared.stores.IdentifiableCollection;

public class InMemoryGameStore implements GameStore {

  private IdentifiableCollection<GameState> games = new IdentifiableCollection<>();
  private IdentifiableCollection<MoveRequests> moveRequests = new IdentifiableCollection<>();

  /**
   * @return a collection containing the game state of ongoing games.
   */
  @Override
  public IdentifiableCollection<GameState> games() {
    return games;
  }

  /**
   * @return a collection containing move requests that were sent to games.
   */
  @Override
  public IdentifiableCollection<MoveRequests> moveRequests() {
    return moveRequests;
  }
}
