package cs3500.pa03;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.ControllerImpl;
import cs3500.pa03.controller.ControllerImpl2;
import cs3500.pa03.model.AiMapPlayer;
import cs3500.pa03.model.AiRandomPlayer;
import cs3500.pa03.model.AiStackPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.PlayerUnsunkShips;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.TerminalView;
import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver2 {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {

    View view = new TerminalView(System.out);
    ControllerImpl2 controller = null;

//    if (args.length == 0) {
//      Random random = new Random();
//      int height = 6;//random.nextInt(6, 16);
//      int width = 6;//random.nextInt(6, 16);
//
//      PlayerUnsunkShips playerUnsunkShips1 = new PlayerUnsunkShips();
//      PlayerUnsunkShips playerUnsunkShips2 = new PlayerUnsunkShips();
//
//      Board playerBoard = new Board(height, width);
//      Board opponentBoard = new Board(height, width);
//
//      Player player1 =
//          new AiPlayer(playerBoard, opponentBoard, playerUnsunkShips1);
//      Player player2 =
//          new AiPlayer(new Board(height, width), new Board(height, width), playerUnsunkShips2);
//      int shipUpper = Math.min(height, width) - 4;
//      Map<ShipType, Integer> specifications = new HashMap<>();
//
//      //int shipAmount = random.nextInt(shipUpper) + 1;
//      specifications.put(ShipType.Carrier, 1);
//      //shipUpper = shipUpper - shipAmount;
//
//      //shipAmount = random.nextInt(shipUpper) + 1;
//      specifications.put(ShipType.Battleship, 1);
//      //shipUpper = shipUpper - shipAmount;
//
//      //shipAmount = random.nextInt(shipUpper) + 1;
//      specifications.put(ShipType.Destroyer, 1);
//      //shipUpper = shipUpper - shipAmount;
//
//      //shipAmount = random.nextInt(shipUpper) + 1;
//      specifications.put(ShipType.Submarine, 1);
//
//      controller = new ControllerImpl(view, player1, player2, specifications, height, width, playerUnsunkShips1, playerUnsunkShips2, playerBoard, opponentBoard);
//    } else if (args.length == 2) {
//
//      String host = args[0];
//      int port = 0;
//
//      try {
//        port = Integer.parseInt(args[1]);
//      } catch (NumberFormatException e) {
//        view.showError("There was an issue with grabbing your port.");
//        System.exit(1);
//      }
//      Socket socket = null;
//      try {
//        socket = new Socket(host, port);
//      } catch (IOException e) {
//        view.showError("There was an issue with generating a socket.");
//        throw new RuntimeException(e);
//        //System.exit(1);
//      }
//      Player player = new AiPlayer4(new Board(), new Board(), new PlayerUnsunkShips());
//      controller = new ProxyController2(socket, player);
//
//    } else {
//      view.showError("There was an issue with the command line arguments.\n Try Again!");
//      System.exit(1);
//    }

    int wins = 0;
    for(int i = 0; i < 100000; i++) {
      Random random = new Random();
      int height = random.nextInt(6, 16);
      int width = random.nextInt(6, 16);

      PlayerUnsunkShips playerUnsunkShips1 = new PlayerUnsunkShips();
      PlayerUnsunkShips playerUnsunkShips2 = new PlayerUnsunkShips();


      int[] randomSetup = generateRandomSetup();
      width = randomSetup[0];
      height = randomSetup[1];
      Board playerBoard = new Board(height, width);
      Board opponentBoard = new Board(height, width);
      Player player1 =
          new AiStackPlayer(playerBoard, opponentBoard, playerUnsunkShips1);
      Player player2 =
          new AiRandomPlayer(new Board(height, width), new Board(height, width), playerUnsunkShips2);
      int shipUpper = Math.min(height, width) - 4;
      Map<ShipType, Integer> specifications = new HashMap<>();

      //int shipAmount = random.nextInt(shipUpper) + 1;
      specifications.put(ShipType.Carrier, randomSetup[2]);
      //shipUpper = shipUpper - shipAmount;

      //shipAmount = random.nextInt(shipUpper) + 1;
      specifications.put(ShipType.Battleship, randomSetup[3]);
      //shipUpper = shipUpper - shipAmount;

      //shipAmount = random.nextInt(shipUpper) + 1;
      specifications.put(ShipType.Destroyer, randomSetup[4]);
      //shipUpper = shipUpper - shipAmount;

      //shipAmount = random.nextInt(shipUpper) + 1;
      specifications.put(ShipType.Submarine, randomSetup[5]);

      controller = new ControllerImpl2(view, player1, player2, specifications, height, width, playerUnsunkShips1, playerUnsunkShips2, playerBoard, opponentBoard);
      controller.run();
      wins+=controller.win();
    }


    System.out.println(wins);

  }

  private static int[] generateRandomSetup() {
    Random random= new Random();
    int width = random.nextInt(6, 16);
    int height = random.nextInt(6, 16);
    int randomRange = Math.min(width, height) - 3;
    List<Integer> ships = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      int res = (int) (random.nextDouble() * randomRange);
      randomRange -= res;
      ships.add(res + 1);
    }
    Collections.shuffle(ships);
    return new int[] {width, height, ships.get(0), ships.get(1),
        ships.get(2), ships.get(3)};
  }
}