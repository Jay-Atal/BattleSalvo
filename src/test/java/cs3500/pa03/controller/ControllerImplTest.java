package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.TerminalView;
import cs3500.pa03.view.View;
import java.io.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerImplTest {
  private String inputNoSeed = """
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
      """;
  private String inputWin = """
      6 6
      3 1 1 1
      0 0
      0 1
      0 2
      0 3
      0 4
      0 5
      1 0
      2 0
      3 0
      4 0
      5 0
      1 1
      2 1
      3 1
      4 1
      5 1
      1 3
      1 4
      2 3
      3 3
      4 3
      5 3
      2 4
      3 4
      4 4
      5 4
      1 5
      2 5
      3 5
      3 2
      3 1
      3 3
      3 4
      2 2
      1 2
      4 2
      5 2""";
  private String inputLoss = """
      6 6 6
      6 asdfadf
      16 16
      6 16
      16 6
      -1 - 1
      6 -1
      -1 6
      -1 16
      16 -1
      15 6
      1 1 1 1 1
      0 1 1 1
      1 1 1 sdfasfdasdfasdsadf
      1 1 1 5
      1 1 1 1
      5 6 7
      5 asdfasdf
      -1 -1
      -1 16
      6 16
      -16 6
      -16 -16
      16 16
      6 16
      16 6
      0 0
      0 1
      0 2
      0 3
      0 4
      0 5
      1 0
      2 0
      3 0
      4 0
      5 0
      1 1
      2 1
      3 1
      4 1
      5 1
      1 3
      1 4
      2 3
      3 3
      4 3
      5 3
      2 4
      3 4
      4 4
      5 4
      1 5
      2 5
      3 5
      3 2
      3 1
      3 3
      3 4
      2 2
      1 2
      4 2
      5 2
      """;

  private String expectedOutputWin = """
      Hello! Welcome to the OOD BattleSalvo Game!
      Please enter a valid height and width below:
      ------------------------------------------------------
      ------------------------------------------------------
      Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
      Remember, your fleet may not exceed size6 .
      ------------------------------------------------------
      Opponent Board Data:
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
          
      Your Board:
      B ` D C C C
      B ` D C C C
      B S D C C C
      B S D C C C
      B S ` C C C
      ` ` ` C C C
          
      Please Enter 6 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      H ` ` ` ` `
      H ` ` ` ` `
      + ` ` ` ` `
      H ` ` ` ` `
      H ` ` ` ` `
      H ` ` ` ` `
          
      Your Board:
      B ` D C H C
      B ` D H C H
      B S D H C C
      B S D H C H
      B S ` C C C
      ` ` ` C C C
          
      Please Enter 6 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      H H H H H +
      H H ` ` ` `
      + ` ` ` ` `
      H ` ` ` ` `
      H ` ` ` ` `
      H ` ` ` ` `
          
      Your Board:
      B ` D H H H
      B ` H H H H
      B S D H C H
      B S H H C H
      B S ` C C C
      ` ` ` C C C
          
      Please Enter 6 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      H H H H H +
      H H H H H H
      + ` ` ` ` `
      H H ` ` ` `
      H H ` ` ` `
      H ` ` ` ` `
          
      Your Board:
      B ` H H H H
      B ` H H H H
      B S H H H H
      B H H H C H
      B S + C C C
      ` ` ` C C C
          
      Please Enter 5 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      H H H H H +
      H H H H H H
      + ` ` ` ` `
      H H H H H H
      H H H ` ` `
      H ` ` ` ` `
          
      Your Board:
      B + H H H H
      B ` H H H H
      B H H H H H
      H H H H H H
      B S + C C C
      ` ` ` C C C
          
      Please Enter 5 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      H H H H H +
      H H H H H H
      + ` ` ` ` `
      H H H H H H
      H H H H H H
      H H H ` ` `
          
      Your Board:
      B + H H H H
      B + H H H H
      H H H H H H
      H H H H H H
      H S + C C C
      ` ` ` C C C
          
      Please Enter 5 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      H H H H H +
      H H H H H H
      + ` ` H ` `
      H H H H H H
      H H H H H H
      H H H + ` `
          
      Your Board:
      B + H H H H
      B + H H H H
      H H H H H H
      H H H H H H
      H H + C C C
      ` ` ` C C C
          
      Please Enter 4 Shots:
      ------------------------------------------------------
      You WIN!
      """;

  String expectedLoss = """
      Hello! Welcome to the OOD BattleSalvo Game!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please only enter just a height and width.
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please make sure to enter valid Integers for height and width
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please remember that the height and width of the game must be in the range (6, 15), inclusive.
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please remember that the height and width of the game must be in the range (6, 15), inclusive.
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please remember that the height and width of the game must be in the range (6, 15), inclusive.
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please only enter just a height and width.
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please remember that the height and width of the game must be in the range (6, 15), inclusive.
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please remember that the height and width of the game must be in the range (6, 15), inclusive.
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please remember that the height and width of the game must be in the range (6, 15), inclusive.
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      Uh Oh! You've entered invalid dimensions.
      Please remember that the height and width of the game must be in the range (6, 15), inclusive.
      Try again!
      Please enter a valid height and width below:
      ------------------------------------------------------
      ------------------------------------------------------
      Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
      Remember, your fleet may not exceed size6 .
      ------------------------------------------------------
      Uh Oh! You've entered invalid fleet sizes.Make Sure to enter 4 and only 4 fleet sizes.
      Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
      Remember, your fleet may not exceed size6 .
      ------------------------------------------------------
      Uh Oh! You've entered invalid fleet sizes.Fleet Amount must be greater than or equal to 1.
      Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
      Remember, your fleet may not exceed size6 .
      ------------------------------------------------------
      Uh Oh! You've entered invalid fleet sizes.Invalid input entered for SUBMARINE
      Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
      Remember, your fleet may not exceed size6 .
      ------------------------------------------------------
      Uh Oh! You've entered invalid fleet sizes.The total fleets should be less than or equal to 6
      Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
      Remember, your fleet may not exceed size6 .
      ------------------------------------------------------
      Opponent Board Data:
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` B S `
      ` ` ` B S `
      C ` D B S `
      C ` D B ` `
      C ` D B ` `
      C ` D ` ` `
      C ` ` ` ` `
      C ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Please Enter 4 Shots:
      ------------------------------------------------------
      Enter only and exactly 2 integers for x and y
      Please Enter 4 Shots:
      ------------------------------------------------------
      Enter Integers Only.
      Please Enter 4 Shots:
      ------------------------------------------------------
      One of the coords was not in range of the board.
      Please Enter 4 Shots:
      ------------------------------------------------------
      One of the coords was not in range of the board.
      Please Enter 4 Shots:
      ------------------------------------------------------
      One of the coords was not in range of the board.
      Please Enter 4 Shots:
      ------------------------------------------------------
      One of the coords was not in range of the board.
      Please Enter 4 Shots:
      ------------------------------------------------------
      One of the coords was not in range of the board.
      Please Enter 4 Shots:
      ------------------------------------------------------
      One of the coords was not in range of the board.
      Please Enter 4 Shots:
      ------------------------------------------------------
      One of the coords was not in range of the board.
      Please Enter 4 Shots:
      ------------------------------------------------------
      One of the coords was not in range of the board.
      Please Enter 4 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + ` ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` B S `
      ` ` ` B S `
      C ` D B S `
      C ` D B ` `
      C ` D B ` `
      C ` D ` ` `
      C ` ` ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` ` ` ` `
      ` ` ` ` + `
        
      Please Enter 4 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` ` ` ` `
      ` ` ` ` ` `
      + ` ` B S `
      ` ` ` B S `
      C ` D B H `
      C ` D B + `
      C ` D B ` `
      C ` D ` ` `
      C ` ` ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` ` ` ` +
      ` ` ` ` + `
        
      Please Enter 4 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + ` ` `
      ` ` ` ` ` `
      + ` ` B S `
      ` ` ` B H `
      C ` D H H +
      C ` D B + `
      C ` D B ` `
      C ` D ` ` `
      C ` ` ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` ` ` ` +
      ` ` ` ` + `
        
      Please Enter 4 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      + ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + ` ` `
      ` ` ` ` ` `
      + ` ` B H `
      ` ` ` H H +
      C ` H H H +
      C ` D B + `
      C ` D B ` `
      C ` D ` ` `
      C ` ` ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` ` ` ` +
      ` ` ` ` + `
        
      Please Enter 3 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` ` ` `
      + + + ` ` `
      + + ` ` ` `
      + ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + ` ` `
      ` ` ` ` ` `
      + ` ` H H `
      ` ` + H H +
      C + H H H +
      C ` H B + `
      C ` D B ` `
      C ` D ` ` `
      C ` ` ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` ` ` ` +
      ` ` ` ` + `
        
      Please Enter 3 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` ` ` `
      + + + H H +
      + + ` ` ` `
      + ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + ` ` `
      ` ` ` + ` `
      + ` + H H `
      ` ` + H H +
      C + H H H +
      C + H H + `
      C ` D B ` `
      C ` D ` ` `
      C ` ` ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` ` ` ` +
      ` ` ` ` + `
        
      Please Enter 3 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` ` ` `
      + + + H H +
      + + + H H `
      + ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + ` ` `
      ` ` ` + + `
      + ` + H H +
      ` ` + H H +
      C + H H H +
      C + H H + `
      C ` H H ` `
      C ` D ` ` `
      C ` ` ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` ` ` ` +
      ` ` ` ` + `
        
      Please Enter 2 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` ` ` `
      + + + H H +
      + + + H H +
      + + ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + ` ` `
      ` ` ` + + `
      + ` + H H +
      ` ` + H H +
      C + H H H +
      C + H H + `
      C + H H + `
      C ` H + ` `
      C ` ` ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` ` ` ` +
      ` ` ` ` + `
        
      Please Enter 1 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` ` ` `
      + + + H H +
      + + + H H +
      + + + ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + + ` `
      ` ` ` + + `
      + ` + H H +
      ` ` + H H +
      H + H H H +
      C + H H + `
      C + H H + `
      C + H + ` `
      C ` + ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` ` ` ` +
      ` ` ` ` + `
        
      Please Enter 1 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` ` ` `
      + + + H H +
      + + + H H +
      + + + H ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + + ` `
      ` ` ` + + `
      + ` + H H +
      + ` + H H +
      H + H H H +
      H + H H + `
      C + H H + +
      C + H + ` `
      C ` + ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` + ` ` +
      ` ` ` ` + `
        
      Please Enter 1 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` H ` `
      + + + H H +
      + + + H H +
      + + + H ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + + ` +
      ` ` ` + + `
      + ` + H H +
      + ` + H H +
      H + H H H +
      H + H H + `
      H + H H + +
      C + H + + `
      C ` + ` ` `
      C ` ` ` + `
      ` ` ` ` ` +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` ` + ` ` +
      ` ` ` + + `
        
      Please Enter 1 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` H ` `
      + + + H H +
      + + + H H +
      + + + H ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + + ` +
      ` ` ` + + `
      + ` + H H +
      + ` + H H +
      H + H H H +
      H + H H + `
      H + H H + +
      H + H + + `
      C ` + + ` `
      C ` ` ` + `
      ` ` ` ` + +
      ` ` ` ` ` `
      ` ` + ` ` `
      ` + + ` ` +
      ` ` ` + + `
        
      Please Enter 1 Shots:
      ------------------------------------------------------
      Opponent Board Data:
      + H + + H +
      + H + + H +
      + ` ` H ` `
      + + + H H +
      + + + H H +
      + + + H ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
      ` ` ` ` ` `
        
      Your Board:
      ` ` + + ` +
      ` ` ` + + `
      + ` + H H +
      + ` + H H +
      H + H H H +
      H + H H + `
      H + H H + +
      H + H + + `
      H ` + + + `
      C ` ` ` + `
      ` ` ` ` + +
      ` ` ` ` + `
      ` + + ` ` `
      ` + + ` ` +
      ` ` ` + + `
        
      Please Enter 1 Shots:
      ------------------------------------------------------
      You LOSE!
      """;

  private String inputDraw = """
      6 6
      3 1 1 1
      0 0
      0 1
      0 2
      0 3
      0 4
      0 5
      1 0
      2 0
      3 0
      4 0
      5 0
      1 1
      2 1
      3 1
      4 1
      5 1
      1 3
      1 4
      2 3
      3 3
      4 3
      5 3
      2 4
      3 4
      4 4
      5 4
      1 5
      2 5
      3 5
      3 2
      3 1
      3 3
      3 4
      2 2
      1 2
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
      4 2
      5 2""";

  private View view;
  private Controller controller;
  private StringBuilder out;

  @BeforeEach
  void setup() {

  }

  @Test
  void runLoss() {
    Readable inputReader = new StringReader(inputLoss);
    out = new StringBuilder();
    view = new TerminalView(out, inputReader);
    controller = new ControllerImpl(view, 1);
    controller.run();
    assertEquals(expectedLoss, out.toString());
  }

  @Test
  void runDraw() {
    Readable inputReader = new StringReader(inputDraw);
    out = new StringBuilder();
    view = new TerminalView(out, inputReader);
    controller = new ControllerImpl(view, 1);
    controller.run();
    assertEquals("You DRAW!\n", out.toString().substring(out.toString().lastIndexOf("You")));
  }

  @Test
  void runWin() {
    Readable inputReader = new StringReader(inputWin);
    out = new StringBuilder();
    view = new TerminalView(out, inputReader);
    controller = new ControllerImpl(view, 1);
    controller.run();
    assertEquals(expectedOutputWin, out.toString());
  }

  @Test
  void runNoSeed() {
    Readable inputReader = new StringReader(inputNoSeed);
    out = new StringBuilder();
    view = new TerminalView(out, inputReader);
    controller = new ControllerImpl(view);
    assertDoesNotThrow(() -> controller.run());
  }
}