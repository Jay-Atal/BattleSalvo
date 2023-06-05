//package cs3500.pa03.model;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import cs3500.pa03.view.TerminalView;
//import cs3500.pa03.view.View;
//import java.io.StringReader;
//import java.util.Map;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class GenericPlayerTest {
//  private GenericPlayer player;
//  private View view;
//  private Appendable output;
//  private Board playerBoard;
//  private Board opponentBoard;
//
//  @BeforeEach
//  public void setup() {
//    output = new StringBuilder();
//    view = new TerminalView(output, new StringReader(""));
//    playerBoard = new Board(6, 6);
//    opponentBoard = new Board(6, 6);
//    player = new HumanPlayer(playerBoard, opponentBoard, view);
//  }
//
//  @Test
//  public void testSetup() {
//    player.setup(6, 6, Map.of(
//        ShipType.Carrier, 1,
//        ShipType.Battleship, 1,
//        ShipType.Destroyer, 1,
//        ShipType.Submarine, 1
//    ));
//    fail();
//  }
//
//  @Test
//  public void testReportDamage() {
//  }
//
//  @Test
//  public void testSuccessfulHits() {
//  }
//
//  @Test
//  public void testEndGame() {
//  }
//
//  @Test
//  public void testUpdateShips() {
//  }
//}