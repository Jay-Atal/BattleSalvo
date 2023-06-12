package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 * Represents a generic player for Battle Salvo.
 */
public abstract class GenericPlayer implements Player {
  static final int CHECK_TIMES = 25;
  protected Board playerBoard;
  protected Board opponentBoard;
  protected List<Ship> ships;
  protected List<Coord> lastShots;
  protected PlayerUnsunkShips unsunkShips;
  protected Integer seed;
  private final Random random;
  protected List<ShipType> shipTypes;

  /**
   * Constructor to make the Generic Player.
   *
   * @param playerBoard   represents the player's board.
   * @param opponentBoard represents the opponent's board from the player's perspective.
   * @param unsunkShips   object representing a player's unsunk ships.
   */
  public GenericPlayer(Board playerBoard, Board opponentBoard, PlayerUnsunkShips unsunkShips) {
    this.playerBoard = playerBoard;
    this.opponentBoard = opponentBoard;
    this.unsunkShips = unsunkShips;
    seed = null;
    random = new Random();
  }

  /**
   * Constructor to make the Generic Player.
   *
   * @param playerBoard   represents the player's board.
   * @param opponentBoard represents the opponent's board from the player's perspective.
   * @param seed          for the random.
   * @param unsunkShips   object representing a player's unsunk ships.
   */
  public GenericPlayer(Board playerBoard, Board opponentBoard, Integer seed,
                       PlayerUnsunkShips unsunkShips) {
    this.playerBoard = playerBoard;
    this.opponentBoard = opponentBoard;
    this.unsunkShips = unsunkShips;
    this.seed = seed;
    random = (seed == null) ? new Random() : new Random(seed);
  }


  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    shipTypes = new ArrayList<>();

    if (playerBoard.board == null) {
      playerBoard = new Board(height, width);
      opponentBoard = new Board(height, width);
    }

    for (ShipType shipType : ShipType.values()) {
      addShipTypes(shipTypes, shipType, specifications.get(shipType));
    }

    ships = placeShips(shipTypes);
    unsunkShips.addAll(ships);
    return ships;
  }

  private void addShipTypes(List<ShipType> shipTypes, ShipType shipType, int times) {
    for (int i = 0; i < times; i++) {
      shipTypes.add(shipType);
    }
  }

  private List<Ship> placeShips(List<ShipType> shipTypes) {

    Stack<Ship> placedShips = new Stack<>();

    for (int i = 0; i < shipTypes.size(); i++) {

      ShipType shipType = shipTypes.get(i);
      Ship shipToAdd = randomShip(shipType);

      if (i == 0) {
        while (true) {
          if (isValidSpot(shipToAdd)) {
            placeShip(shipToAdd);
            placedShips.push(shipToAdd);
            break;
          }
          shipToAdd = randomShip(shipType);
        }
        continue;
      }

      for (int j = 0; j <= CHECK_TIMES; j++) {
        if (isValidSpot(shipToAdd)) {
          placeShip(shipToAdd);
          placedShips.push(shipToAdd);
          break;
        }
        shipToAdd = randomShip(shipType);
        if (j == CHECK_TIMES) {
          removeShip(placedShips.pop());
          i -= 2;

        }
      }
    }
    return placedShips.stream().toList();
  }


  protected Ship randomShip(ShipType shipType) {
    int x = random.nextInt(0, playerBoard.board.length);
    int y = random.nextInt(0, playerBoard.board[0].length);
    boolean isHorizontal = random.nextBoolean();
    Direction direction = (isHorizontal) ? Direction.HORIZONTAL : Direction.VERTICAL;
    Coord coord = new Coord(x, y);
    return new Ship(shipType, coord, direction);
  }

  private void placeShip(Ship ship) {
//    Coord coord = ship.coord();
//    ShipType shipType = ship.shipType();
//    Direction direction = ship.direction();
//
//    for (int i = 0; i < shipType.getSize(); i++) {
//      if (direction.equals(Direction.HORIZONTAL)) {
//        playerBoard.board[coord.y()][coord.x() + i] = new Cell(coord, ship);
//      } else {
//        playerBoard.board[coord.y() + i][coord.x()] = new Cell(coord, ship);
//      }
//    }
    placeShip(ship, playerBoard);
  }

  protected void placeShip(Ship ship, Board board) {
    Coord coord = ship.coord();
    ShipType shipType = ship.shipType();
    Direction direction = ship.direction();

    for (int i = 0; i < shipType.getSize(); i++) {
      if (direction.equals(Direction.HORIZONTAL)) {
        board.board[coord.y()][coord.x() + i] = new Cell(coord, ship);
      } else {
        board.board[coord.y() + i][coord.x()] = new Cell(coord, ship);
      }
    }

  }


  private void removeShip(Ship ship) {
    removeShip(ship, playerBoard);
//    Direction direction = ship.direction();
//    Coord coord = ship.coord();
//    for (int i = 0; i < ship.shipType().getSize(); i++) {
//      if (direction.equals(Direction.HORIZONTAL)) {
//        playerBoard.board[coord.y()][i + coord.x()] = new Cell(coord, Condition.WATER);
//      } else {
//        playerBoard.board[i + coord.y()][coord.x()] = new Cell(coord, Condition.WATER);
//      }
//    }

  }

  protected void removeShip(Ship ship, Board board) {
    Direction direction = ship.direction();
    Coord coord = ship.coord();

    for (int i = 0; i < ship.shipType().getSize(); i++) {
      if (direction.equals(Direction.HORIZONTAL)) {
        board.board[coord.y()][i + coord.x()] = new Cell(coord, Condition.WATER);
      } else {
        board.board[i + coord.y()][coord.x()] = new Cell(coord, Condition.WATER);
      }
    }

  }

  protected boolean isValidSpot(Ship ship) {
    return isValidSpot(ship, playerBoard);
//    Coord coord = ship.coord();
//    ShipType shipType = ship.shipType();
//    Direction direction = ship.direction();
//
//    for (int i = 0; i < shipType.getSize(); i++) {
//      Cell cell;
//      try {
//        cell =
//            (direction.equals(Direction.HORIZONTAL)) ? playerBoard.board[coord.y()][coord.x() + i] :
//                playerBoard.board[coord.y() + i][coord.x()];
//      } catch (IndexOutOfBoundsException e) {
//        return false;
//      }
//      Condition cellCondition = cell.getCondition();
//      if (cellCondition.equals(Condition.SHIP)) {
//        return false;
//      }
//    }
//    return true;
  }

  protected boolean isValidSpot(Ship ship, Board board) {

    Coord coord = ship.coord();
    ShipType shipType = ship.shipType();
    Direction direction = ship.direction();

    for (int i = 0; i < shipType.getSize(); i++) {
      Cell cell;
      try {
        cell =
            (direction.equals(Direction.HORIZONTAL)) ? board.board[coord.y()][coord.x() + i] :
                board.board[coord.y() + i][coord.x()];
      } catch (IndexOutOfBoundsException e) {
        return false;
      }
      Condition cellCondition = cell.getCondition();
      if (cellCondition.equals(
          Condition.MISS) || cellCondition.equals(Condition.SHIP)) { //cellCondition.equals(Condition.HIT) previous condition
        return false;
      }
    }
    return true;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   * ship on this board.
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> shotsHit = new ArrayList<>();

    for (Coord shot : opponentShotsOnBoard) {
      Cell shotCell = playerBoard.board[shot.y()][shot.x()];
      if (shotCell.getCondition().equals(Condition.SHIP)
          || shotCell.getCondition().equals(Condition.HIT)) {
        shotsHit.add(shot);
        playerBoard.board[shot.y()][shot.x()] = new Cell(shot, Condition.HIT);
      } else {
        playerBoard.board[shot.y()][shot.x()] = new Cell(shot, Condition.MISS);
      }
    }
    return shotsHit;
  }


  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord shot : shotsThatHitOpponentShips) {
      opponentBoard.board[shot.y()][shot.x()] = new Cell(shot, Condition.HIT);
    }
    updateShips();
  }


  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    //Not needed right now
    return;
  }

  private boolean isSunk(Ship ship) {
    boolean isHorizontal = (ship.direction().equals(Direction.HORIZONTAL));
    Coord start = ship.coord();

    for (int i = 0; i < ship.shipType().getSize(); i++) {
      Cell cell = (isHorizontal) ? playerBoard.board[start.y()][i + start.x()] :
          playerBoard.board[start.y() + i][start.x()];
      if (!cell.getCondition().equals(Condition.HIT)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Method to update the ships list to get rid of ships that have sunk.
   */
  public void updateShips() {
    List<Ship> shipsClone = new ArrayList<>(ships);
    for (int i = shipsClone.size() - 1; i >= 0; i--) {
      if (isSunk(shipsClone.get(i))) {
        shipsClone.remove(i);
      }
    }

    ships = new ArrayList<>(shipsClone);
    unsunkShips.clear();
    unsunkShips.addAll(ships);
  }

}