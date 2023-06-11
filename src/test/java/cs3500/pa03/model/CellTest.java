package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellTest {
  Cell cellHit;
  Cell cellWater;
  Cell cellMiss;
  @BeforeEach
  void setUp() {
     cellHit = new Cell(new Coord(0,0), Condition.HIT);
     cellWater = new Cell(new Coord(0,0), Condition.WATER);
     cellMiss = new Cell(new Coord(0,0), Condition.MISS);
  }

  @Test
  void getCondition() {
    assertEquals(cellHit.getCondition(), Condition.HIT);
    assertEquals(cellWater.getCondition(), Condition.WATER);
    assertEquals(cellMiss.getCondition(), Condition.MISS);
  }

  @Test
  void getStartLetter() {
    assertEquals('H', cellHit.getStartLetter());
    assertEquals('+', cellMiss.getStartLetter());
    assertEquals('`', cellWater.getStartLetter());
  }
}