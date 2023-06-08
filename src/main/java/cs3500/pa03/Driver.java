package cs3500.pa03;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.ControllerImpl;
import cs3500.pa03.controller.ProxyController;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.PlayerUnsunkShips;
import cs3500.pa03.view.TerminalView;
import cs3500.pa03.view.View;
import java.io.IOException;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    View view = new TerminalView(System.out);
    Controller controller = null;

    if(args.length == 0) {
      controller = new ControllerImpl(view);
    } else if (args.length == 2) {

      String host = args[0];
      int port = 0;

      try {
        port = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        view.showError("There was an issue with grabbing your port.");
        System.exit(1);
      }
      Socket socket = null;
      try {
         socket = new Socket(host, port);
      } catch (IOException e) {
        view.showError("There was an issue with generating a socket.");
        System.exit(1);
      }
      Player player = new AiPlayer(new Board(), new Board(), new PlayerUnsunkShips());
      controller = new ProxyController(socket, player);

    } else  {
      view.showError("There was an issue with the command line arguments.\n Try Again!");
      System.exit(1);
    }

    controller.run();
  }
}