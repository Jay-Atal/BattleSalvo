package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a ship type.
 */
public enum ShipType {
  @JsonProperty("CARRIER") CARRIER(6, 'C'),
  @JsonProperty("BATTLESHIP") BATTLESHIP(5, 'B'),
  @JsonProperty("DESTROYER") DESTROYER(4, 'D'),
  @JsonProperty("SUBMARINE") SUBMARINE(3, 'S');

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
