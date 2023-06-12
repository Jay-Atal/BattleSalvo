package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa03.Mocket;
import cs3500.pa03.json.JsonUtils;
import cs3500.pa03.json.MessageJson;
import cs3500.pa03.json.VolleyJSON;
import cs3500.pa03.model.AiStackPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.PlayerUnsunkShips;
import cs3500.pa03.model.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProxyControllerTest {
  private ByteArrayOutputStream testLog;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void joinTest() {
    ObjectMapper mapper = new ObjectMapper();
    MessageJson messageJson = new MessageJson("join", mapper.createObjectNode());
    JsonNode message = JsonUtils.serializeRecord(messageJson);

    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    PlayerUnsunkShips playerUnsunkShips1 = new PlayerUnsunkShips();
    Board playerBoard = new Board(6, 6);
    Board opponentBoard = new Board(6, 6);
    Player player = new AiStackPlayer(playerBoard, opponentBoard, playerUnsunkShips1);

    ProxyController proxyController = new ProxyController(socket, player);
    proxyController.run();

    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":\"Jay-Atal\",\"game-type"
        + "\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void setupTest() {
    ObjectNode mapper = new ObjectMapper().createObjectNode();
    mapper.put("width", 6);
    mapper.put("height", 6);

    PlayerUnsunkShips playerUnsunkShips1 = new PlayerUnsunkShips();
    Board playerBoard = new Board(6, 6);
    Board opponentBoard = new Board(6, 6);
    Player player = new AiStackPlayer(playerBoard, opponentBoard, playerUnsunkShips1, 0);
    ObjectNode fleetSpec = new ObjectMapper().createObjectNode();
    fleetSpec.put(ShipType.CARRIER.name(), 1);
    fleetSpec.put(ShipType.BATTLESHIP.name(), 1);
    fleetSpec.put(ShipType.DESTROYER.name(), 1);
    fleetSpec.put(ShipType.SUBMARINE.name(), 1);
    mapper.set("fleet-spec", fleetSpec);

    MessageJson messageJson = new MessageJson("setup", mapper);
    JsonNode message = JsonUtils.serializeRecord(messageJson);

    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));
    ProxyController proxyController = new ProxyController(socket, player);
    proxyController.run();

    String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":4,\"y"
        + "\":0},\"length\":6,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":0,\"y\":0},\"length\":5"
        + ",\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":1,\"y\":2},\"length\":4,\"direction\":\"V"
        + "ERTICAL\"},{\"coord\":{\"x\":3,\"y\":0},\"length\":3,\"direction\":\"VERTICAL\"}]}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void takeShotsTest() {
    ObjectMapper mapper = new ObjectMapper();
    MessageJson messageJson = new MessageJson("take-shots", mapper.createObjectNode());
    JsonNode message = JsonUtils.serializeRecord(messageJson);
    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    PlayerUnsunkShips playerUnsunkShips1 = new PlayerUnsunkShips();
    Board playerBoard = new Board(6, 6);
    Board opponentBoard = new Board(6, 6);
    Player player = new AiStackPlayer(playerBoard, opponentBoard, playerUnsunkShips1, 0);

    HashMap<ShipType, Integer> specifications = new HashMap();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    player.setup(6,6, specifications);

    ProxyController proxyController = new ProxyController(socket, player);
    proxyController.run();

    String expected = "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":"
        + "[{\"x\":0,\"y\":4},{\"x\":5,\"y\":3},{\"x\":3,\"y\":0},{\"x\":0,\"y\":1}]}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void reportDamageTest() {
    ObjectNode mapper = new ObjectMapper().createObjectNode();
    List<Coord> shots = new ArrayList<>();
    shots.addAll(List.of(new Coord(0, 0), new Coord(1, 1), new Coord(2, 2)));
    VolleyJSON volley = new VolleyJSON(shots);

    MessageJson messageJson =
        new MessageJson("report-damage", JsonUtils.serializeRecord(volley));
    JsonNode message = JsonUtils.serializeRecord(messageJson);
    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    PlayerUnsunkShips playerUnsunkShips1 = new PlayerUnsunkShips();
    Board playerBoard = new Board(6, 6);
    Board opponentBoard = new Board(6, 6);
    Player player = new AiStackPlayer(playerBoard, opponentBoard, playerUnsunkShips1, 0);

    HashMap<ShipType, Integer> specifications = new HashMap();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    player.setup(6,6, specifications);

    ProxyController proxyController = new ProxyController(socket, player);
    proxyController.run();

    String expected = "{\"method-name\":\"report-damage\",\"arguments\":"
        + "{\"coordinates\":[{\"x\":0,\"y\":0}]}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
    } catch (IOException e) {
      fail();
    }
  }
}
