package cs3500.pa03.model;

import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a human player playing Battle Salvo.
 */
public class HumanPlayer extends GenericPlayer {
  private final View view;


  public HumanPlayer(Board playerBoard, Board opponentBoard, View view,
                     PlayerUnsunkShips unsunkShips) {
    super(playerBoard, opponentBoard, unsunkShips);
    this.view = view;
  }

  public HumanPlayer(Board playerBoard, Board opponentBoard, View view,
                     PlayerUnsunkShips unsunkShips, Integer seed) {
    super(playerBoard, opponentBoard, seed, unsunkShips);
    this.view = view;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "Human Player";
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    updateShips();
    List<Coord> toReturnShots;
    while (true) {
      toReturnShots = new ArrayList<>();
      view.promptTakeShots("Please Enter " + ships.size() + " Shots:");
      view.line();
      int i;
      for (i = 0; i < ships.size(); i++) {
        try {
          String shot = view.getShot();
          String[] shotSplit = shot.split(" ");

          if (shotSplit.length != 2) {
            throw new RuntimeException("Enter only and exactly 2 integers for x and y");
          }
          int x;
          int y;
          try {
            x = Integer.parseInt(shotSplit[0]);
            y = Integer.parseInt(shotSplit[1]);
            toReturnShots.add(new Coord(x, y));
          } catch (NumberFormatException e) {
            throw new RuntimeException("Enter Integers Only.");
          }
          if (!(x >= 0 && x < playerBoard.board.length)
              || !(y >= 0 && y < playerBoard.board[0].length)) {
            throw new RuntimeException("One of the coords was not in range of the board.");
          }
        } catch (RuntimeException e) {
          view.showError(e.getMessage());
          break;
        }

      }
      if (i >= ships.size()) {
        break;
      }
    }
    lastShots = toReturnShots;
    updateShips();
    return updateShotBoard(toReturnShots);
  }

  private List<Coord> updateShotBoard(List<Coord> shots) {

    for (int i = 0; i < shots.size(); i++) {
      Coord shot = shots.get(i);
      //System.out.println(shot);
      opponentBoard.board[shot.y()][shot.x()] = new Cell(shot, Condition.MISS);
    }
    return shots;
  }

}
