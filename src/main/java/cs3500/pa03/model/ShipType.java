package cs3500.pa03.model;

/**
 * Represents a ship type.
 */
public enum ShipType {
  Carrier(6, 'C'),
  Battleship(5, 'B'),
  Destroyer(4, 'D'),
  Submarine(3, 'S');

  private final int size;
  private final char startLetter;

  //Intellij said the private keyword is "redundant" because already private.
  ShipType(int size, char startLetter) {
    this.size = size;
    this.startLetter = startLetter;
  }

  public int getSize() {
    return size;
  }

  public char getStartLetter() {
    return startLetter;
  }
}
