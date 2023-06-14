package cs3500.pa03.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.json.EndGameRequestJson;
import cs3500.pa03.json.FleetJson;
import cs3500.pa03.json.JoinJson;
import cs3500.pa03.json.JsonUtils;
import cs3500.pa03.json.MessageJson;
import cs3500.pa03.json.SetupRequestJson;
import cs3500.pa03.json.ShipAdapterJson;
import cs3500.pa03.json.VolleyJson;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * ProxyController controls the game against a server for a given player and socket.
 */
public class ProxyController implements Controller {

  private final Socket server;
  private final Player player;

  private final PrintStream out;
  private final InputStream in;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Makes a ProxyController object.
   *
   * @param server the server where the address and port number are.
   * @param player the player used to play against the server.
   */
  public ProxyController(Socket server, Player player) {
    this.server = server;
    this.player = player;
    try {
      out = new PrintStream(server.getOutputStream());
      in = server.getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Decodes the message and passes it to it's appropriate method.
   *
   * @param message the message that is decoded.
   */
  private void delegateMessage(MessageJson message) {
    String methodName = message.methodName();
    JsonNode arguments = message.arguments();
    switch (methodName) {
      case "join" -> join();
      case "setup" -> setup(arguments);
      case "take-shots" -> takeShots();
      case "report-damage" -> reportDamage(arguments);
      case "successful-hits" -> successfulHits(arguments);
      default -> endGame(arguments);
    }
  }

  /**
   * Run the game.
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      System.out.println("End of input.");
    }
  }

  private void sentToServer(String methodName, Record record) {
    MessageJson response;
    if (record == null) {
      response = new MessageJson(methodName, new ObjectMapper().createObjectNode());
    } else {
      JsonNode arguments = JsonUtils.serializeRecord(record);
      response = new MessageJson(methodName, arguments);
    }
    JsonNode toPrint = JsonUtils.serializeRecord(response);
    out.println(toPrint);
  }


  private void join() {
    Record joinJson = new JoinJson("Jay-Atal", "SINGLE");
    sentToServer("join", joinJson);
  }

  private void setup(JsonNode arguments) {
    SetupRequestJson setupArgs = this.mapper.convertValue(arguments, SetupRequestJson.class);

    int height = setupArgs.height();
    int width = setupArgs.width();

    Map<ShipType, Integer> specifications = setupArgs.specifications();
    List<Ship> shipsList = player.setup(height, width, specifications);
    List<ShipAdapterJson> shipAdapterJsonList = new ArrayList<>();

    for (Ship ship : shipsList) {
      ShipAdapterJson shipAdapterJson =
          new ShipAdapterJson(ship.coord(), ship.shipType().getSize(), ship.direction());
      shipAdapterJsonList.add(shipAdapterJson);
    }

    Record returnArgs = new FleetJson(shipAdapterJsonList);
    sentToServer("setup", returnArgs);
  }

  private void takeShots() {
    VolleyJson volleyJson = new VolleyJson(player.takeShots());
    sentToServer("take-shots", volleyJson);
  }

  private void reportDamage(JsonNode arguments) {
    VolleyJson input = this.mapper.convertValue(arguments, VolleyJson.class);
    VolleyJson volleyJson = new VolleyJson(player.reportDamage(input.shots()));
    sentToServer("report-damage", volleyJson);
  }

  private void successfulHits(JsonNode arguments) {
    VolleyJson input = this.mapper.convertValue(arguments, VolleyJson.class);
    player.successfulHits(input.shots());
    sentToServer("successful-hits", null);
  }

  private void endGame(JsonNode arguments) {
    EndGameRequestJson input = this.mapper.convertValue(arguments, EndGameRequestJson.class);
    player.endGame(input.gameResult(), input.reason());
    try {
      server.close();
    } catch (IOException e) {
      System.out.println("Failed to close server");
    }
    sentToServer("end-game", null);
  }
}