package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.PlayerUnsunkShips;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.View;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The implementation of the Battle Salvo Controller.
 */
public class ControllerImpl implements Controller {
  private final View view;
  private int height;
  private int width;

  private HashMap<ShipType, Integer> specifications;
  private Board playerBoard;
  private Board opponentBoard;

  private Integer seed;
  private final PlayerUnsunkShips player1UnsunkShips;
  private final PlayerUnsunkShips player2UnsunkShips;
  private Player player1;
  private Player player2;

  public ControllerImpl(View view) {
    this.view = view;
    player1UnsunkShips = new PlayerUnsunkShips();
    player2UnsunkShips = new PlayerUnsunkShips();
  }

  public ControllerImpl(View view, Integer seed) {
    this(view);
    this.seed = seed;
  }

  /**
   * Getting all the inputs from the user.
   */
  public void getInputs() {

    view.welcomeUser("Hello! Welcome to the OOD BattleSalvo Game!");
    view.promptHeightWidth("Please enter a valid height and width below:");
    view.line();
    HeightWidth heightWidth = getHeightWidth();

    height = heightWidth.height();
    width = heightWidth.width();

    playerBoard = new Board(height, width);
    opponentBoard = new Board(height, width);

    view.line();
    view.promptFleetSelection("""
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size""" + Math.min(height, width) + " .");
    view.line();

    this.specifications = getSpecifications();
  }

  public void setUpPlayers() {

    if (seed != null) {
      player1 = new HumanPlayer(playerBoard, opponentBoard, view, player1UnsunkShips, seed);
      player2 =
          new AiPlayer(new Board(height, width), new Board(height, width), player2UnsunkShips,
              (seed + 1));
    } else {
      player1 = new HumanPlayer(playerBoard, opponentBoard, view, player1UnsunkShips);
      player2 =
          new AiPlayer(new Board(height, width), new Board(height, width), player2UnsunkShips);
    }
  }

  @Override
  public void run() {
    getInputs();
    setUpPlayers();

    player1.setup(height, width, specifications);
    player2.setup(height, width, specifications);

    while (true) {
      view.showBoard("Opponent Board Data:", opponentBoard.getBoardArray());
      view.showBoard("Your Board:", playerBoard.getBoardArray());

      List<Coord> player1Shots = player1.takeShots();
      List<Coord> player2Shots = player2.takeShots();

      List<Coord> play1SuccessfulHits = player2.reportDamage(player1Shots);
      List<Coord> play2SuccessfulHits = player1.reportDamage(player2Shots);

      player1.successfulHits(play1SuccessfulHits);
      player2.successfulHits(play2SuccessfulHits);

      List<Ship> player1UnsunkShipsList = player1UnsunkShips.getShotList();
      List<Ship> player2UnsunkShipsList = player2UnsunkShips.getShotList();

      if (player1UnsunkShipsList.size() == 0 || player2UnsunkShipsList.size() == 0) {
        if (player1UnsunkShipsList.size() == 0 && player2UnsunkShipsList.size() == 0) {
          view.showResult(GameResult.DRAW);
          break;
        }
        if (player1UnsunkShipsList.size() == 0) {
          view.showResult(GameResult.LOSE);
        } else {
          view.showResult(GameResult.WIN);
        }
        break;
      }


    }


  }

  private HeightWidth getHeightWidth() {
    String standardError = "Uh Oh! You've entered invalid dimensions.\n";
    String outOfRangeError =
        "Please remember that the height and width of the game must be in the "
            + "range (6, 15), inclusive.\n"
            + "Try again!";
    String incorrectInputAmount = "Please only enter just a height and width.\nTry again!";
    String parseError = "Please make sure to enter valid Integers for height and width\nTry again!";

    int height = 0;
    int width = 0;

    while (true) {
      try {
        String input = view.getHeightWidth();

        if (input.split(" ").length != 2) {
          throw new RuntimeException(incorrectInputAmount);
        }

        String[] inputs = input.split(" ");

        try {
          height = Integer.parseInt(inputs[0]);
          width = Integer.parseInt(inputs[1]);
        } catch (NumberFormatException e) {
          throw new NoSuchElementException(parseError);
        }

        if (!(height >= 6 && height <= 15) || !(width >= 6 && width <= 15)) {
          throw new RuntimeException(outOfRangeError);
        }
        break;

      } catch (RuntimeException e) {
        view.showError(standardError + e.getMessage());
        view.promptHeightWidth("Please enter a valid height and width below:");
        view.line();
      }
    }
    return new HeightWidth(height, width);
  }

  private HashMap<ShipType, Integer> getSpecifications() {
    HashMap<ShipType, Integer> specifications = null;
    String standardError = "Uh Oh! You've entered invalid fleet sizes.";
    while (true) {
      specifications = new HashMap<>();
      try {
        String input = view.getFleetSelection();
        String[] fleets = input.split(" ");

        if (fleets.length != 4) {
          throw new RuntimeException("Make Sure to enter 4 and only 4 fleet sizes.");
        }
        int sum = 0;
        for (int i = 0; i < fleets.length; i++) {
          try {
            int feelAmount = Integer.parseInt(fleets[i]);
            if (feelAmount < 1) {
              throw new RuntimeException("Fleet Amount must be greater than or equal to 1.");
            }
            specifications.put(ShipType.values()[i], feelAmount);
            sum += feelAmount;
          } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid input entered for " + ShipType.values()[i]);
          }
          if (sum > Math.min(height, width)) {
            throw new RuntimeException(
                "The total fleets should be less than or equal to " + Math.min(height, width));
          }
        }
        break;
      } catch (RuntimeException e) {
        view.showError(standardError + e.getMessage());
        view.promptFleetSelection("""
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size""" + Math.min(height, width) + " .");
        view.line();
      }
    }
    return specifications;
  }
}
