package cs3500.pa03.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.json.EndGameRequestJson;
import cs3500.pa03.json.FleetJson;
import cs3500.pa03.json.JoinJson;
import cs3500.pa03.json.SetupRequestJson;
import cs3500.pa03.json.ShipAdapterJSON;
import cs3500.pa03.json.VolleyJSON;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import cs3500.pa03.json.JsonUtils;
import cs3500.pa03.json.MessageJson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProxyController implements Controller {

  private final Socket server;
  private final Player player;

  private final PrintStream out;
  private InputStream in;
  private final ObjectMapper mapper = new ObjectMapper();

  private int wins = 0;

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
    switch (methodName) {
      case "join" -> join();
      case "setup" -> setup(arguments);
      case "take-shots" -> takeShots();
      case "report-damage" -> reportDamage(arguments);
      case "successful-hits" -> successfulHits(arguments);
      case "end-game" -> endGame(arguments);
      //TODO: Add Default
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
      // Disconnected from server or parsing exception
      // TODO??
    }
  }

  private void sentToServer(String methodName, Record record) {
    JsonNode arguments = JsonUtils.serializeRecord(record);
    MessageJson response = new MessageJson(methodName, arguments);
    JsonNode toPrint = JsonUtils.serializeRecord(response);
    out.println(toPrint);
  }

  public void join() {
    Record joinJson = new JoinJson("Jay-Atal", "SINGLE");
    sentToServer("join", joinJson);
  }

  public void setup(JsonNode arguments) {
    SetupRequestJson setupArgs = this.mapper.convertValue(arguments, SetupRequestJson.class);

    int height = setupArgs.height();
    int width = setupArgs.width();

    Map<ShipType, Integer> specifications = setupArgs.specifications();
    List<Ship> shipsList = player.setup(height, width, specifications);
    List<ShipAdapterJSON> shipAdapterJSONList = new ArrayList<>();

    for (Ship ship : shipsList) {
      ShipAdapterJSON shipAdapterJSON =
          new ShipAdapterJSON(ship.coord(), ship.shipType().getSize(), ship.direction());
      shipAdapterJSONList.add(shipAdapterJSON);
    }

    Record returnArgs = new FleetJson(shipAdapterJSONList);
    sentToServer("setup", returnArgs);
  }

  public void takeShots() {
    VolleyJSON volleyJSON = new VolleyJSON(player.takeShots());
    sentToServer("take-shots", volleyJSON);
  }

  public void reportDamage(JsonNode arguments) {
    VolleyJSON input = this.mapper.convertValue(arguments, VolleyJSON.class);
    VolleyJSON volleyJSON = new VolleyJSON(player.reportDamage(input.shots()));
    sentToServer("report-damage", volleyJSON);
  }

  public void successfulHits(JsonNode arguments) {
    VolleyJSON input = this.mapper.convertValue(arguments, VolleyJSON.class);
    player.successfulHits(input.shots());
    sentToServer("successful-hits", null);
  }

  public void endGame(JsonNode arguments) {
    EndGameRequestJson input = this.mapper.convertValue(arguments, EndGameRequestJson.class);
    if(input.gameResult().equals(GameResult.WIN)) {
      wins++;
    }
    player.endGame(input.gameResult(), input.reason());
  }
//
//  methodName=setup,arguments=
//  {
//    "fleet":[{
//    "shipType":"CARRIER", "coord":{
//      "x":1, "y":1
//    },"direction":"HORIZONTAL"
//  },{
//    "shipType":"BATTLESHIP", "coord":{
//      "x":0, "y":0
//    },"direction":"HORIZONTAL"
//  },{
//    "shipType":"DESTROYER", "coord":{
//      "x":0, "y":2
//    },"direction":"HORIZONTAL"
//  },{
//    "shipType":"SUBMARINE", "coord":{
//      "x":0, "y":3
//    },"direction":"HORIZONTAL"
//  }
}