package tech.subluminal.server.logic.game;

import java.util.Map;

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
}
