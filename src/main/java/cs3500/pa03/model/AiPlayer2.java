package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

/**
 * Representing the AI player for the game.
 */
public class AiPlayer2 extends GenericPlayer {

  List<Coord> validMoves;

  Stack<Coord> hitCells;

  /**
   * Creates a new AiPlayer.
   *
   * @param playerBoard       the player's board.
   * @param opponentBoard     the player's representation of the opponent's board.
   * @param playerUnsunkShips the object for keeping track of unsunk ships.
   */
  public AiPlayer2(Board playerBoard, Board opponentBoard, PlayerUnsunkShips playerUnsunkShips) {
    super(playerBoard, opponentBoard, playerUnsunkShips);
    validMoves = new ArrayList<>();
    hitCells = new Stack<>();
  }

  /**
   * Creates a new AiPlayer.
   *
   * @param playerBoard       the player's board.
   * @param opponentBoard     the player's representation of the opponent's board.
   * @param playerUnsunkShips the object for keeping track of unsunk ships.
   * @param seed              used for testing purposes to give consitanty "random" placements.
   */
  public AiPlayer2(Board playerBoard, Board opponentBoard, PlayerUnsunkShips playerUnsunkShips,
                   Integer seed) {
    super(playerBoard, opponentBoard, seed, playerUnsunkShips);
    validMoves = new ArrayList<>();
    hitCells = new Stack<>();
  }

  private boolean isValidSpot(Coord coord) {
    return coord.x() < opponentBoard.board.length && coord.y() < opponentBoard.board[0].length &&
        !validMoves.contains(coord);
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "AI-Player";
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
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < ships.size(); i++) {
      if (hitCells.size() > 0) {
        Coord current = hitCells.pop();
        Coord[] neighbords = new Coord[4];
        neighbords[0] = new Coord(current.x() - 1, current.y());
        neighbords[1] = new Coord(current.x() + 1, current.y());
        neighbords[2] = new Coord(current.x(), current.y() - 1);
        neighbords[3] = new Coord(current.x(), current.y() + 1);
        if (shots.size() == ships.size()) {
          return shots;
        }
        for (int j = 0; j < 4; j++) {
          if (isValidSpot(neighbords[i])) {
            validMoves.remove(neighbords[i]);
            shots.add(neighbords[i]);
          }
        }

      }
    }

    Random random = (Objects.equals(seed, null)) ? new Random() : new Random(seed);

    for (int i = 0; i < ships.size() - shots.size(); i++) {
      int randomIndex = random.nextInt(0, validMoves.size());
      shots.add(validMoves.remove(randomIndex));
    }

    lastShots = shots;
    updateShips();
    unsunkShips.clear();
    unsunkShips.addAll(ships);
    return shots;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    super.successfulHits(shotsThatHitOpponentShips);
    for (Coord shot : shotsThatHitOpponentShips) {
      for (int i = validMoves.size() - 1; i > 0; i--) {
        Coord move = validMoves.get(i);
        if (move.equals(shot)) {
          validMoves.remove(i);
        }
      }
      hitCells.push(shot);
    }
  }


}
