package tech.subluminal.shared.records.game;

import tech.subluminal.shared.records.game.Coordinates;

public class Fleet extends GameObject {

  private int numberOfShips;

  public Fleet(Coordinates coordinates, int numberOfShips, String id) {
    super(coordinates, id);
    this.numberOfShips = numberOfShips;
  }

  /**
   * @return the number of ships this fleet contains.
   */
  public int getNumberOfShips() {
    return numberOfShips;
  }

  /**
   * @param numberOfShips the number of ships this fleet contains.
   */
  public void setNumberOfShips(int numberOfShips) {
    this.numberOfShips = numberOfShips;
  }
}
