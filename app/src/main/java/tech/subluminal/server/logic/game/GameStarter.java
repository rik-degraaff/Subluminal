package tech.subluminal.server.logic.game;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.Player;

/**
 * provides an entry point for starting a game.
 */
public interface GameStarter {

  /**
   * Starts a new game.
   *
   * @param lobbyID the id of the lobby this game belongs to.
   * @param players the players participating in this game.
   */
  void startGame(String lobbyID, Map<String, String> players);

  /**
   * Starts a new, customizable game.
   *
   * @param gameID the id of the game.
   * @param players the players participating in this game.
   * @param initialState the initial state of this game.
   * @param afterTick an action to perform after every tick. (when true is sent, this means the
   * game
   */
  void startGame(
      String gameID, Map<String, String> players, GameState initialState,
      Function<Boolean, Boolean> afterTick, Consumer<List<Player>> onEnd
  );
}
