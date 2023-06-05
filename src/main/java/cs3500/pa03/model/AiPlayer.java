package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Representing the AI player for the game.
 */
public class AiPlayer extends GenericPlayer {

  List<Coord> validMoves;

  /**
   * Creates a new AiPlayer.
   *
   * @param playerBoard       the player's board.
   * @param opponentBoard     the player's representation of the opponent's board.
   * @param playerUnsunkShips the object for keeping track of unsunk ships.
   * @param seed              used for testing purposes to give consitanty "random" placements.
   */
  public AiPlayer(Board playerBoard, Board opponentBoard, PlayerUnsunkShips playerUnsunkShips,
                  Integer seed) {
    super(playerBoard, opponentBoard, seed, playerUnsunkShips);
    validMoves = new ArrayList<>();
    for (int r = 0; r < opponentBoard.board.length; r++) {
      for (int c = 0; c < opponentBoard.board[0].length; c++) {
        validMoves.add(new Coord(c, r));
      }
    }
  }

  /**
   * Creates a new AiPlayer.
   *
   * @param playerBoard       the player's board.
   * @param opponentBoard     the player's representation of the opponent's board.
   * @param playerUnsunkShips the object for keeping track of unsunk ships.
   */
  public AiPlayer(Board playerBoard, Board opponentBoard, PlayerUnsunkShips playerUnsunkShips) {
    super(playerBoard, opponentBoard, playerUnsunkShips);
    validMoves = new ArrayList<>();
    for (int r = 0; r < opponentBoard.board.length; r++) {
      for (int c = 0; c < opponentBoard.board[0].length; c++) {
        validMoves.add(new Coord(c, r));
      }
    }
    seed = null;
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
    Random random = (Objects.equals(seed, null)) ? new Random() : new Random(seed);
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < ships.size(); i++) {
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
    }
  }


}
