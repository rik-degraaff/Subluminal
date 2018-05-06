package tech.subluminal.client.presentation;

import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

public interface GamePresenter {

  void setGameDelegate(Delegate delegate);

  void setUserID();

  void update();

  void setPlayerColors(Map<String, Color> playerColors);

  void removeFleets(List<String> fleetIDs);

  void onEndGame(String gameID, String winnerID);

  void removeMotherShips(List<String> removedMotherShips);

  void setGameID(String gameID);

  interface Delegate {

    void sendShips(List<String> stars, int amount);

    void sendMothership(List<String> star);

    void leaveGame();
  }
}
