package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

/**
 * Representing the AI player for the game.
 */
public class AiStackPlayer extends GenericPlayer {

  List<Coord> validMoves;
  List<Coord> usedMoves;
  Stack<Coord> hitCells;


  /**
   * Creates a new AiPlayer.
   *
   * @param playerBoard       the player's board.
   * @param opponentBoard     the player's representation of the opponent's board.
   * @param playerUnsunkShips the object for keeping track of unsunk ships.
   * @param seed              used for testing purposes to give consitanty "random" placements.
   */
  public AiStackPlayer(Board playerBoard, Board opponentBoard, PlayerUnsunkShips playerUnsunkShips,
                       Integer seed) {
    super(playerBoard, opponentBoard, seed, playerUnsunkShips);
    validMoves = new ArrayList<>();
    usedMoves = new ArrayList<>();
    hitCells = new Stack<>();

  }

  private List<Ship> possibleShipLocations = new ArrayList<>();

  private void calculateFrequencies() {
    for (ShipType shipType : shipTypes) {
      for (int r = 0; r < playerBoard.board.length; r++) {
        for (int c = 0; c < playerBoard.board[r].length; c++) {
          for (Direction direction : Direction.values()) {
            Ship toAdd = new Ship(shipType, new Coord(c, r), direction);
            if (!isValidSpot(toAdd)) {
              continue;
            }
//            possibleShipLocations.add(toAdd.coord());
          }

        }
      }
    }


//    for each ship {
//      for each possible ship location {
//        for every other ship {
//          for every possible other-ship location {
//            if the two ships would overlap, save to list of incompatible locations
//          }
//        }
//      }
//      for ()

  }

  private boolean isValidSpot(Coord coord) {
//    return coord.x() < opponentBoard.board.length && coord.y() < opponentBoard.board[0].length &&
//        !validMoves.contains(coord);
    return !usedMoves.contains(coord) && validMoves.contains(coord);
  }

  /**
   * Creates a new AiPlayer.
   *
   * @param playerBoard       the player's board.
   * @param opponentBoard     the player's representation of the opponent's board.
   * @param playerUnsunkShips the object for keeping track of unsunk ships.
   */
  public AiStackPlayer(Board playerBoard, Board opponentBoard, PlayerUnsunkShips playerUnsunkShips) {
    super(playerBoard, opponentBoard, playerUnsunkShips);
    validMoves = new ArrayList<>();
    usedMoves = new ArrayList<>();
    hitCells = new Stack<>();
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> toReturn = super.setup(height, width, specifications);
    for (int r = 0; r < opponentBoard.board.length; r++) {
      for (int c = 0; c < opponentBoard.board[0].length; c++) {
        validMoves.add(new Coord(c, r));
      }
    }
    return toReturn;
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



  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    super.successfulHits(shotsThatHitOpponentShips);
    for(Coord shot: shotsThatHitOpponentShips) {
      hitCells.push(shot);
    }
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
//    View view = new TerminalView(System.out);
//    view.showBoard("3 Player:", playerBoard.getBoardArray());
//    view.showBoard("3 Opponent:", opponentBoard.getBoardArray());

    for (int i = 0; i < ships.size(); i++) {
      if (hitCells.size() > 0) {
        Coord current = hitCells.pop();
        Coord[] neighbors = new Coord[4];
        neighbors[0] = new Coord(current.x() - 1, current.y());
        neighbors[1] = new Coord(current.x() + 1, current.y());
        neighbors[2] = new Coord(current.x(), current.y() - 1);
        neighbors[3] = new Coord(current.x(), current.y() + 1);

        if (shots.size() == ships.size()) {
          usedMoves.addAll(shots);
          return shots;
        }

        for (int j = 0; j < 4; j++) {
          if (isValidSpot(neighbors[j])) {
            validMoves.remove(neighbors[j]);
            shots.add(neighbors[j]);
          }
          if (shots.size() == ships.size()) {
            hitCells.push(current);
            usedMoves.addAll(shots);
            return shots;
          }
        }

      }
    }

    Random random = (Objects.equals(seed, null)) ? new Random() : new Random(seed);
    int shotsSize = shots.size();
    for (int i = 0; i < ships.size() - shotsSize; i++) {
      if(validMoves.size() == 0) {
        return shots;
      }
      int randomIndex = random.nextInt(0, validMoves.size());
      shots.add(validMoves.remove(randomIndex));
    }

    for(int i = 0; i < shots.size(); i++) {
      Coord shot = shots.get(i);
      opponentBoard.board[shot.y()][shot.x()] = new Cell(shot, Condition.MISS);
    }

    lastShots = new ArrayList<>(shots);
    updateShips();
    unsunkShips.clear();
    unsunkShips.addAll(ships);
    usedMoves.addAll(shots);
    return shots;
  }
}