package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerUnsunkShipsTest {
  private PlayerUnsunkShips playerUnsunkShips;

  @BeforeEach
  void setup() {
    playerUnsunkShips = new PlayerUnsunkShips();
  }

  @Test
  void add() {
    Ship horizontalShip = new Ship(ShipType.CARRIER, new Coord(0, 0), Direction.HORIZONTAL);
    playerUnsunkShips.add(horizontalShip);
    List<Ship> expected = new ArrayList<>();
    expected.add(horizontalShip);
    assertEquals(expected, playerUnsunkShips.getShotList());
  }

  @Test
  void addAll() {
    Ship horizontalShip = new Ship(ShipType.CARRIER, new Coord(0, 0), Direction.HORIZONTAL);
    Ship horizontalShip2 = new Ship(ShipType.BATTLESHIP, new Coord(2, 3), Direction.HORIZONTAL);
    Ship verticalShip = new Ship(ShipType.SUBMARINE, new Coord(1, 1), Direction.VERTICAL);
    playerUnsunkShips.addAll(List.of(horizontalShip, horizontalShip2, verticalShip));
    List<Ship> expected = new ArrayList<>(List.of(horizontalShip, horizontalShip2, verticalShip));
    assertEquals(expected, playerUnsunkShips.getShotList());
  }

  @Test
  void clear() {
    Ship horizontalShip = new Ship(ShipType.CARRIER, new Coord(0, 0), Direction.HORIZONTAL);
    Ship horizontalShip2 = new Ship(ShipType.BATTLESHIP, new Coord(2, 3), Direction.HORIZONTAL);
    Ship verticalShip = new Ship(ShipType.SUBMARINE, new Coord(1, 1), Direction.VERTICAL);
    playerUnsunkShips.addAll(List.of(horizontalShip, horizontalShip2, verticalShip));
    List<Ship> expected = new ArrayList<>();
    playerUnsunkShips.clear();
    assertEquals(expected, playerUnsunkShips.getShotList());
  }
}