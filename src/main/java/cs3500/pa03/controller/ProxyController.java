package cs3500.pa03.controller;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa03.model.Player;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import json.MessageJson;

public class ProxyController implements Controller {

  private final Socket server;
  private final Player player;

  private final PrintStream out;

  public ProxyController(Socket server, Player player) {
    this.server = server;
    this.player = player;
    try {
      out = new PrintStream(server.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
      //TODO: Add custom message or handle "gracefully"
    }
  }

  public void delegateMessage(MessageJson message) {
    String methodName = message.methodName();
    JsonNode arguments = message.arguments();
//    if(methodName.equals("join")) {
//      join();
//    }
    //TODO: Add if else-if else statement to call the 6 JSON Message formats.
    //if("join".equals())
  }

  /**
   * Run the game.
   */
  @Override
  public void run() {

  }

  public void join() {
    out.println("""
        
        """);
  }

  public void setup() {

  }

  public void takeShots() {

  }

  public void reportDamage() {

  }

  public void successfulHits() {

  }

  public void endGame() {

  }

}
