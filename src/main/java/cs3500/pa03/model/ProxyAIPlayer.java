package cs3500.pa03.model;

import java.util.List;
import java.util.Map;

public class ProxyAIPlayer extends AiPlayer{
  /**
   * Creates a new AiPlayer.
   *
   * @param playerBoard       the player's board.
   * @param opponentBoard     the player's representation of the opponent's board.
   * @param playerUnsunkShips the object for keeping track of unsunk ships.
   * @param seed              used for testing purposes to give consitanty "random" placements.
   */
  public ProxyAIPlayer(Board playerBoard, Board opponentBoard, PlayerUnsunkShips playerUnsunkShips,
                       Integer seed) {
    super(playerBoard, opponentBoard, playerUnsunkShips, seed);
  }

  /**
   * Creates a new AiPlayer.
   *
   * @param playerBoard       the player's board.
   * @param opponentBoard     the player's representation of the opponent's board.
   * @param playerUnsunkShips the object for keeping track of unsunk ships.
   */
  public ProxyAIPlayer(Board playerBoard, Board opponentBoard,
                       PlayerUnsunkShips playerUnsunkShips) {
    super(playerBoard, opponentBoard, playerUnsunkShips);
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {

    return super.setup(height, width, specifications);
  }
}
