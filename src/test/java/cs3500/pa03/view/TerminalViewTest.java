package cs3500.pa03.view;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.GameResult;
import java.io.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TerminalViewTest {
  View view;
  StringBuilder output;

  @BeforeEach
  public void setUp() {
    output = new StringBuilder();
    view = new TerminalView(output);
  }

  @Test
  void welcomeUser() {
    String welcome = "Hello! Welcome to the OOD BattleSalvo Game!";
    view.welcomeUser(welcome);
    assertEquals(welcome + "\n", output.toString());
  }

  @Test
  void promptHeightWidth() {
    String prompt = "Please enter a valid height and width below:";
    view.promptHeightWidth(prompt);
    assertEquals(prompt + "\n", output.toString());
  }

  @Test
  void getHeightWidth() {
    String mock = new String("Test Input");
    Readable input = new StringReader(mock);
    view = new TerminalView(output, input);
    String actual = view.getHeightWidth();
    assertEquals(mock, actual);
  }

  @Test
  void promptFleetSelection() {
    String prompt = "Please enter a valid height and width below:";
    view.promptFleetSelection(prompt);
    assertEquals(prompt + "\n", output.toString());
  }

  @Test
  void getFleetSelection() {
    String mock = new String("Test Input");
    Readable input = new StringReader(mock);
    view = new TerminalView(output, input);
    String actual = view.getFleetSelection();
    assertEquals(mock, actual);
  }

  @Test
  void showBoard() {
    char[][] testArray = new char[6][6];

    for (int r = 0; r < testArray.length; r++) {
      for (int c = 0; c < testArray[r].length; c++) {
        testArray[r][c] = 'X';
      }
    }

    view.showBoard("Board:", testArray);
    String expectedOutput = """
        Board:
        X X X X X X
        X X X X X X
        X X X X X X
        X X X X X X
        X X X X X X
        X X X X X X

        """;
    assertEquals(expectedOutput, output.toString());
  }

  @Test
  void promptTakeShots() {
    String prompt = "Please enter a valid height and width below:";
    view.promptTakeShots(prompt);
    assertEquals(prompt + "\n", output.toString());
  }

  @Test
  void line() {
    view.line();
    assertEquals("------------------------------------------------------" + "\n",
        output.toString());
  }

  @Test
  void getShot() {
    String mock = new String("Test Input");
    Readable input = new StringReader(mock);
    view = new TerminalView(output, input);
    String actual = view.getShot();
    assertEquals(mock, actual);
  }

  @Test
  void showError() {
    String prompt = "This is an error!";
    view.promptTakeShots(prompt);
    assertEquals(prompt + "\n", output.toString());
  }

  @Test
  void showResult() {
    String result = "You " + GameResult.WIN + "!";
    view.showResult(GameResult.WIN);
    assertEquals(result + "\n", output.toString());
  }
}