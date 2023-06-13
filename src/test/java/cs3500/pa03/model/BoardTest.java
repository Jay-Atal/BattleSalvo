package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class BoardTest {
  @Test
  void testEmptyConstructor() {
    Board nullArrayBoard = new Board();
    assertNull(nullArrayBoard.board);
  }

  @Test
  void getBoardArray() {
    Board board = new Board(6, 6);
    char[][] fakeInitialBoard = new char[6][6];
    for (int r = 0; r < fakeInitialBoard.length; r++) {
      for (int c = 0; c < fakeInitialBoard[r].length; c++) {
        fakeInitialBoard[r][c] = '`';
      }
    }
    assertArrayEquals(fakeInitialBoard, board.getBoardArray());
  }
}