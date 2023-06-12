//package cs3500.pa03.model;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import cs3500.pa03.view.TerminalView;
//import cs3500.pa03.view.View;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class HumanPlayerTest {
//  Player humanPlayer;
//  Board opponentBoard;
//  Board playerBoard;
//  Integer seed;
//
//  View view;
//  PlayerUnsunkShips playerUnsunkShips;
//
//  @BeforeEach
//  void setup() {
//    view = new TerminalView(System.out);
//    seed = 1;
//    playerBoard = new Board(6, 6);
//    opponentBoard = new Board(6, 6);
//    humanPlayer = new HumanPlayer(playerBoard, opponentBoard, view, playerUnsunkShips, seed);
//    HashMap<ShipType, Integer> specifications = new HashMap<>();
//    specifications.put(ShipType.CARRIER, 1);
//    specifications.put(ShipType.BATTLESHIP, 1);
//    specifications.put(ShipType.DESTROYER, 1);
//    specifications.put(ShipType.SUBMARINE, 1);
//
//    humanPlayer.setup(6, 6, specifications);
//  }
//
//  @Test
//  void name() {
//    assertEquals("Human Player", humanPlayer.name());
//  }
//
//
//  @Test
//  void reportDamagePlusSuccessfulHits() {
//    List<Coord> fakeShots = new ArrayList<>();
//    fakeShots.add(new Coord(3, 2));
//    fakeShots.add(new Coord(4, 2));
//    fakeShots.add(new Coord(3, 5));
//    fakeShots.add(new Coord(5, 2));
//    fakeShots.add(new Coord(2, 5));
//    fakeShots.add(new Coord(0, 4));
//    fakeShots.add(new Coord(1, 4));
//    List<Coord> reportDamageExpected = new ArrayList<>();
//    reportDamageExpected.add(new Coord(3, 2));
//    reportDamageExpected.add(new Coord(3, 5));
//    reportDamageExpected.add(new Coord(2, 5));
//    reportDamageExpected.add(new Coord(1, 4));
//
//    assertEquals(reportDamageExpected, humanPlayer.reportDamage(fakeShots));
//  }
//
//}