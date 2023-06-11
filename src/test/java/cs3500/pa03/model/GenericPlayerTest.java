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
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    expectedPlayerShip = new ArrayList<>();
    expectedPlayerShip.add(new Ship(ShipType.CARRIER, new Coord(3, 0), Direction.VERTICAL));
    expectedPlayerShip.add(new Ship(ShipType.BATTLESHIP, new Coord(2, 1), Direction.VERTICAL));
    expectedPlayerShip.add(new Ship(ShipType.DESTROYER, new Coord(0, 0), Direction.VERTICAL));
    expectedPlayerShip.add(new Ship(ShipType.SUBMARINE, new Coord(1, 2), Direction.VERTICAL));
  }

  @Test
  void setup() {
    List<Ship> playerShips = player.setup(6, 6, specifications);
    assertEquals(expectedPlayerShip, playerShips);
  }

  @Test
  void takeShots() {
    player.setup(6, 6, specifications);
    List<Coord> expectedShots = new ArrayList<>();
    expectedShots.add(new Coord(3,5));
    expectedShots.add(new Coord(0,3));
    expectedShots.add(new Coord(4,4));
    expectedShots.add(new Coord(2,5));
    assertEquals(expectedShots, player.takeShots());
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