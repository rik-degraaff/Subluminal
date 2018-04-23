package tech.subluminal.server.logic.game;

import java.util.Set;

public interface GameStarter {

  void startGame(String lobbyID, Set<String> playerIDs);
}
