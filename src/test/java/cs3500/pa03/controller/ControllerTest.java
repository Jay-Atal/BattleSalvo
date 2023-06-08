package cs3500.pa03.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.TerminalView;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerTest {
  StringBuilder output;
  StringReader input;
  Controller controller;
  String outputExpected;

  StringReader errorInput;
  String outputError;

  StringReader lostInput;

  @BeforeEach
  public void setUp() {
    output = new StringBuilder();
    inputSetUp();
    outputExpected();
    outputErrorSetup();
    errorInputSetup();
    setLostInput();
  }

  public void setLostInput() {
    lostInput = new StringReader("""
        6 6
        1 1 1 1
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        0 0
        """);
  }


  public void inputSetUp() {
    input = new StringReader("""
        6 6
        1 1 1 1
        3 2
        4 2
        5 2
        0 4
        1 4
        2 4
        3 4
        1 0
        2 0
        3 0
        4 0
        5 0
        0 3
        1 3
        2 3
        3 3
        4 3
        5 3
        0 0
        0 0""");
  }

  public void outputExpected() {
    outputExpected = """
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size6 .
        ------------------------------------------------------
        Opponent Board Data:
        0 0 0 0 0 0
        0 0 0 0 0 0
        0 0 0 0 0 0
        0 0 0 0 0 0
        0 0 0 0 0 0
        0 0 0 0 0 0

        Your Board:
        D 0 0 C 0 0
        D 0 B C 0 0
        D S B C 0 0
        D S B C 0 0
        0 S B C 0 0
        0 0 B C 0 0

        Please Enter 4 Shots:
        ------------------------------------------------------
        Opponent Board Data:
        0 0 0 0 0 0
        0 0 0 0 0 0
        0 0 0 H H H
        0 0 0 0 0 0
        H 0 0 0 0 0
        0 0 0 0 0 0

        Your Board:
        D 0 0 C M 0
        D 0 B C 0 0
        D S B H 0 0
        D S B H 0 M
        0 S B C 0 0
        0 0 B C 0 0

        Please Enter 4 Shots:
        ------------------------------------------------------
        Opponent Board Data:
        0 H 0 0 0 0
        0 0 0 0 0 0
        0 0 0 H H H
        0 0 0 0 0 0
        H H H H 0 0
        0 0 0 0 0 0

        Your Board:
        D 0 M C M 0
        D 0 B C 0 0
        D S B H 0 0
        D S B H 0 M
        0 H B C 0 M
        0 0 B C 0 0

        Please Enter 4 Shots:
        ------------------------------------------------------
        Opponent Board Data:
        0 H H H H H
        0 0 0 0 0 0
        0 0 0 H H H
        0 0 0 0 0 0
        H H H H 0 0
        0 0 0 0 0 0

        Your Board:
        D 0 M C M 0
        D 0 B C M 0
        D S B H 0 0
        D S H H 0 M
        0 H B C 0 M
        0 0 B C 0 0

        Please Enter 4 Shots:
        ------------------------------------------------------
        Opponent Board Data:
        0 H H H H H
        0 0 0 0 0 0
        0 0 0 H H H
        H H H H 0 0
        H H H H 0 0
        0 0 0 0 0 0

        Your Board:
        D 0 M C M 0
        D 0 B C M 0
        D S B H 0 0
        D S H H 0 M
        0 H B C 0 M
        0 M B C 0 0

        Please Enter 4 Shots:
        ------------------------------------------------------
        You WIN!
        """;
  }

  void errorInputSetup() {
    errorInput = new StringReader("""
        16 16
        5 5
        5 16
        16 5
        6 16
        6 5
        16 6
        5 16
        6 6 7
        6 F
        6 6
        1 1 1 1 1
        0 1 1 1
        f f f f
        2 2 2 2
        1 1 1 1
        1 1 1
        -1 4
        9 4
        4 -1
        4 9
        -1 -1
        9 9
        3 2
        4 2
        5 2
        0 4
        1 4
        2 4
        3 4
        1 0
        2 0
        3 0
        4 0
        5 0
        0 3
        1 3
        2 3
        3 3
        4 3
        5 3
        0 0
        0 0""");
  }

  void outputErrorSetup() {
    Scanner file = null;
    try {
      file = new Scanner(Path.of("src/test/resources/outputError.txt"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    StringBuilder buildFile = new StringBuilder();
    while (file.hasNext()) {
      buildFile.append(file.nextLine());
      buildFile.append("\n");
    }
    outputError = buildFile.toString();

  }

  @Test
  public void integrationTest() {
    controller = new ControllerImpl(new TerminalView(output, input), 1);
    controller.run();
    assertEquals(outputExpected, output.toString());
  }

  @Test
  public void integrationTestErrors() {
    controller = new ControllerImpl(new TerminalView(output, errorInput), 1);
    controller.run();
    assertEquals(outputError, output.toString());
  }

  @Test
  void lossTest() {
    controller = new ControllerImpl(new TerminalView(output, lostInput), 1);
    controller.run();
    String sb = output.toString();
    assertEquals("You LOSE!\n", sb.substring(sb.indexOf("You ")));
  }


}