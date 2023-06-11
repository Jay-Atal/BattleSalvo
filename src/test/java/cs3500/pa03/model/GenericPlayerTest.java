package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  Board playerBoard;
  Board opponentBoard;
  PlayerUnsunkShips playerUnsunkShips;
  Integer seed;
  Player player;
  Map<ShipType, Integer> specifications;

  List<Ship> expectedPlayerShip;

  @BeforeEach
  void setUp() {
    playerBoard = new Board();
    opponentBoard = new Board();
    playerUnsunkShips = new PlayerUnsunkShips();
    seed = 1;
    player = new AiStackPlayer(playerBoard, opponentBoard, playerUnsunkShips, seed);
    specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    expectedPlayerShip.add(new Ship(ShipType.Carrier, new Coord(3, 0), Direction.VERTICAL));
    expectedPlayerShip.add(new Ship(ShipType.Battleship, new Coord(2, 1), Direction.VERTICAL));
    expectedPlayerShip.add(new Ship(ShipType.Destroyer, new Coord(0, 0), Direction.VERTICAL));
    expectedPlayerShip.add(new Ship(ShipType.Submarine, new Coord(1, 2), Direction.VERTICAL));
  }

  @Test
  void setup() {
    List<Ship> playerShips = player.setup(6, 6, specifications);
    assertEquals(expectedPlayerShip, playerShips);
  }

  @Test
  void takeShots() {

  }

  @Test
  void reportDamage() {
  }

  @Test
  void successfulHits() {
  }

  @Test
  void endGame() {
  }

  @Test
  void updateShips() {
  }
}