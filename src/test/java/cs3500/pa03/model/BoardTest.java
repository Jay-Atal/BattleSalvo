package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
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
    for (char[] chars : fakeInitialBoard) {
      Arrays.fill(chars, '`');
    }
    assertArrayEquals(fakeInitialBoard, board.getBoardArray());
  }
}