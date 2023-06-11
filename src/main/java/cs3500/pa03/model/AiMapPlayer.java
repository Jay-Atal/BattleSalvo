package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

/**
 * Representing the AI player for the game.
 */
public class AiMapPlayer extends GenericPlayer {

  List<Coord> validMoves;
  List<Coord> usedMoves;
  Stack<Coord> hitCells;
  Map<Coord, Integer> heatMap = new HashMap<>();
  int turn = 0;
  int shipAmount;
  int opponentShips;


  /**
   * Creates a new AiPlayer.
   *
   * @param playerBoard       the player's board.
   * @param opponentBoard     the player's representation of the opponent's board.
   * @param playerUnsunkShips the object for keeping track of unsunk ships.
   * @param seed              used for testing purposes to give consitanty "random" placements.
   */
  public AiMapPlayer(Board playerBoard, Board opponentBoard, PlayerUnsunkShips playerUnsunkShips,
                     Integer seed) {
    super(playerBoard, opponentBoard, seed, playerUnsunkShips);
    validMoves = new ArrayList<>();
    usedMoves = new ArrayList<>();
    hitCells = new Stack<>();
  }

  private List<Ship> possibleShipLocations = new ArrayList<>();

  private void calculateFrequencies() {
    heatMap = new HashMap<>();
    for (ShipType shipType : shipTypes) {
      for (int r = 0; r < opponentBoard.board.length; r++) {
        for (int c = 0; c < opponentBoard.board[r].length; c++) {
          Coord current = new Coord(c, r);
          Ship ship;
          for (Direction direction : Direction.values()) {
            ship = new Ship(shipType, current, direction);
            if (isValidSpot(ship, opponentBoard)) {
              for (int i = 0; i < ship.shipType().getSize(); i++) {
                Coord toIncrease = (ship.direction().equals(Direction.HORIZONTAL)) ?
                    new Coord(current.x() + i, current.y()) :
                    new Coord(current.x(), current.y() + i);
                int currentFrequency = heatMap.getOrDefault(toIncrease, 0);
                int increaseAmount =
                    (opponentBoard.board[toIncrease.y()][toIncrease.x()].getCondition()
                        .equals(Condition.HIT)) ? 25 : 1;
                heatMap.put(toIncrease, currentFrequency + increaseAmount);
              }

            }
          }
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
  public AiMapPlayer(Board playerBoard, Board opponentBoard, PlayerUnsunkShips playerUnsunkShips) {
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
    shipAmount = ships.size();
    opponentShips = shipAmount;
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
    for (Coord shot : shotsThatHitOpponentShips) {
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


    if (turn > 1 && shipAmount == ships.size()) {
      calculateFrequencies();
      List<Coord> orderedShots = new ArrayList<>(heatMap.keySet());
      orderedShots.sort(Comparator.comparingInt(heatMap::get).reversed());
      int shotsSize = shots.size();
      for (int i = 0; i < ships.size() - shotsSize; i++) {
        if (isValidSpot(orderedShots.get(i))) {
          Coord shot = orderedShots.get(i);
          shots.add(shot);
          usedMoves.add(shot);
        }
        if (shots.size() == ships.size()) {
          usedMoves.addAll(shots);
          return updateShotBoard(shots);
        }
      }
    }
    turn++;

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
          return updateShotBoard(shots);
        }

        for (int j = 0; j < 4; j++) {
          if (isValidSpot(neighbors[j])) {
            validMoves.remove(neighbors[j]);
            shots.add(neighbors[j]);
          }
          if (shots.size() == ships.size()) {
            hitCells.push(current);
            usedMoves.addAll(shots);
            return updateShotBoard(shots);
          }
        }

      }
    }

    Random random = (Objects.equals(seed, null)) ? new Random() : new Random(seed);
    int shotsSize = shots.size();
    for (int i = 0; i < ships.size() - shotsSize; i++) {
      if (validMoves.size() == 0) {
        return updateShotBoard(shots);
      }
      int randomIndex = random.nextInt(0, validMoves.size());
      Coord shot = validMoves.remove(randomIndex);
      shots.add(shot);
    }


    lastShots = new ArrayList<>(shots);
    updateShips();
    unsunkShips.clear();
    unsunkShips.addAll(ships);
    usedMoves.addAll(shots);
    return updateShotBoard(shots);
  }

  private List<Coord> updateShotBoard(List<Coord> shots) {

    for (int i = 0; i < shots.size(); i++) {
      Coord shot = shots.get(i);
      opponentBoard.board[shot.y()][shot.x()] = new Cell(shot, Condition.MISS);
    }
    usedMoves.addAll(shots);
    usedMoves.removeAll(shots);
    return shots;
  }

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    opponentShips = opponentShotsOnBoard.size();
    return super.reportDamage(opponentShotsOnBoard);
  }
}