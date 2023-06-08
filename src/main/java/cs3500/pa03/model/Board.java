package cs3500.pa03.model;

/**
 * Represents a board object.
 */
public class Board {
  protected Cell[][] board;

  /**
   * Constructor to make a board.
   *
   * @param height is the height of the board.
   * @param width  is the width of the board.
   */
  public Board(int height, int width) {
    board = new Cell[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        board[r][c] = new Cell(new Coord(c, r), Condition.WATER);
      }
    }
  }

  public Board() {
    board = null;
  }

  /**
   *
   * @return a array representing the board with chars of the starting letter of the cell condition.
   */
  public char[][] getBoardArray() {
    char[][] charArray = new char[board.length][];

    for (int r = 0; r < board.length; r++) {
      charArray[r] = new char[board[r].length];
      for (int c = 0; c < board[r].length; c++) {
        charArray[r][c] = board[r][c].getStartLetter();
      }
    }

    return charArray;
  }
}
