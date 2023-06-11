package cs3500.pa03.model;

import java.util.HashMap;

/**
 * Represents a Cell on the board containing a condition and a coordinate.
 */
public class Cell {
  protected Coord coord;

  public Condition getCondition() {
    return condition;
  }

  private Condition condition;
  private HashMap<Condition, Character> charLookup = new HashMap<>();

  private void setUpTable() {
//    charLookup.put(Condition.HIT, 'H');
//    charLookup.put(Condition.MISS, 'M');
//    charLookup.put(Condition.WATER, '0');
    charLookup.put(Condition.HIT, 'H');
    charLookup.put(Condition.MISS, '+');
    charLookup.put(Condition.WATER, '`');
    //default ship but overridden in the constructor.
    charLookup.put(Condition.SHIP, '_');
  }

  /**
   * Makes a Cell object
   *
   * @param coord     the Coordinate x y position.
   * @param condition the condition/ state of the cell.
   */
  public Cell(Coord coord, Condition condition) {
    setUpTable();
    this.coord = coord;
    this.condition = condition;
  }

  /**
   * Makes a Cell object
   *
   * @param coord the Coordinate x y position.
   * @param ship  the Ship that is on the cell and the condition is Ship.
   */
  public Cell(Coord coord, Ship ship) {
    setUpTable();
    this.coord = coord;
    condition = Condition.SHIP;
    charLookup.remove(Condition.SHIP);
    char startLetter = ship.shipType().getStartLetter();
    charLookup.put(Condition.SHIP, startLetter);
  }

  public char getStartLetter() {
    return charLookup.get(condition);
  }
}
