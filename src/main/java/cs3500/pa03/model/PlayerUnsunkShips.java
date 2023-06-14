package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a object keeping track of the unsunk ships.
 */
public class PlayerUnsunkShips {
  private final List<Ship> shipList;

  public PlayerUnsunkShips() {
    shipList = new ArrayList<>();
  }

  public List<Ship> getShotList() {
    return shipList;
  }

  /**
   * Add single ship.
   *
   * @param ship that is being added.
   */
  public void add(Ship ship) {
    shipList.add(ship);
  }

  /**
   * Adds all Ships from list.
   *
   * @param shipsToAdd the list used to get the ships to add.
   */
  public void addAll(List<Ship> shipsToAdd) {
    shipList.addAll(shipsToAdd);
  }

  /**
   * Clears the list of unsunk ships.
   */
  public void clear() {
    if (shipList.size() > 0) {
      shipList.subList(0, shipList.size()).clear();
    }
  }
}
