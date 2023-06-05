package cs3500.pa03.view;

import cs3500.pa03.model.GameResult;

/**
 * Represents a Battle Salvo game view.
 */
public interface View {

  void welcomeUser(String welcome);

  void promptHeightWidth(String prompt);

  String getHeightWidth();

  void promptFleetSelection(String prompt);

  String getFleetSelection();

  void showBoard(String message, char[][] board);

  void promptTakeShots(String prompt);

  void line();

  String getShot();

  void showError(String error);

  void showResult(GameResult gameResult);
}
