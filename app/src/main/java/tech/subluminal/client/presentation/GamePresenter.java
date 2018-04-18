package tech.subluminal.client.presentation;

import java.util.List;

public interface GamePresenter {

  void setGameDelegate(Delegate delegate);

  void setUserID();

  void update();

  interface Delegate {

    void sendShips(List<String> stars, int amount);

    void sendMothership(List<String> star);
  }
}
