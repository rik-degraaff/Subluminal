package tech.subluminal.shared.stores.records.game;

import java.util.List;

public class Fleet extends Movable {

  private int numberOfShips;

  public Fleet(Coordinates coordinates, int numberOfShips, String id, List<String> targetIDs) {
    super(coordinates, id, targetIDs);
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
