package cs3500.pa03.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.json.JoinJson;
import cs3500.pa03.model.Player;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import cs3500.pa03.json.JsonUtils;
import cs3500.pa03.json.MessageJson;

public class ProxyController implements Controller {

  private final Socket server;
  private final Player player;

  private final PrintStream out;
  private InputStream in;
  private final ObjectMapper mapper = new ObjectMapper();

  public ProxyController(Socket server, Player player) {
    this.server = server;
    this.player = player;
    try {
      out = new PrintStream(server.getOutputStream());
      in = server.getInputStream();
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
    switch (methodName) {
      case "join" -> join();
      case "setup" -> setup(arguments);
      case "take-shots" -> takeShots();
      case "report-damage" -> reportDamage(arguments);
      case "successful-hits" -> successfulHits(arguments);
      case "end-game" -> endGame(arguments);
    }
  }

  /**
   * Run the game.
   */
  @Override
  public void run() {
    System.out.println("BeforeTry");
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      System.out.println("Try");
      while (!this.server.isClosed()) {
        System.out.println("Server not closed");
        MessageJson message = parser.readValueAs(MessageJson.class);
        System.out.println(message);
        delegateMessage(message);
      }
      System.out.println("server closed");
    } catch (IOException e) {
      // Disconnected from server or parsing exception
      // TODO??
    }
  }

  private void sentToServer(MessageJson response){
    JsonNode toPrint = JsonUtils.serializeRecord(response);
    out.println(toPrint);
  }

  public void join() {
    Record joinJson = new JoinJson("Jay-Atal", "SINGLE");
    JsonNode returnArgs = JsonUtils.serializeRecord(joinJson);
    MessageJson response = new MessageJson("join", returnArgs);
    sentToServer(response);
  }

  public void setup(JsonNode arguments) {

  }

  public void takeShots() {

  }

  public void reportDamage(JsonNode arguments) {

  }

  public void successfulHits(JsonNode arguments) {

  }

  public void endGame(JsonNode arguments) {

  }
}