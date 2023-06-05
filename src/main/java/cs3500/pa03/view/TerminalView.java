package cs3500.pa03.view;

import cs3500.pa03.model.GameResult;
import java.io.IOException;
import java.util.Scanner;

/**
 * A view that represent the view for terminal.
 */
public class TerminalView implements View {
  private final Appendable out;
  private final Scanner input;

  public TerminalView(Appendable out) {
    input = new Scanner(System.in);
    this.out = out;
  }

  public TerminalView(Appendable out, Readable in) {
    input = new Scanner(in);
    this.out = out;
  }

  private void showOutput(String output) {
    try {
      out.append(output).append("\n");
    } catch (IOException e) {
      throw new RuntimeException("""
          Unexpected Error Occurred.
          Sorry for the inconvenience.""", e);
    }
  }

  @Override
  public void welcomeUser(String welcome) {
    showOutput(welcome);
  }

  @Override
  public void promptHeightWidth(String prompt) {
    showOutput(prompt);
  }

  @Override
  public String getHeightWidth() {
    return input.nextLine();
  }

  @Override
  public void promptFleetSelection(String prompt) {
    showOutput(prompt);
  }

  @Override
  public String getFleetSelection() {
    return input.nextLine();
  }

  @Override
  public void showBoard(String message, char[][] board) {
    StringBuilder totalOutput = new StringBuilder();
    totalOutput.append(message);
    totalOutput.append("\n");

    StringBuilder builderLine;

    for (char[] line : board) {
      builderLine = new StringBuilder();
      boolean first = true;
      for (char letter : line) {
        if (!first) {
          builderLine.append(" ");
        }
        first = false;
        builderLine.append(letter);
      }
      totalOutput.append(builderLine);
      totalOutput.append("\n");
    }
    showOutput(totalOutput.toString());
  }

  @Override
  public void promptTakeShots(String prompt) {
    showOutput(prompt);
  }

  @Override
  public void line() {
    showOutput("------------------------------------------------------");
  }

  @Override
  public String getShot() {
    return input.nextLine();
  }

  @Override
  public void showError(String error) {
    showOutput(error);
  }

  @Override
  public void showResult(GameResult gameResult) {
    showOutput("The game results in a " + gameResult);
  }
}
