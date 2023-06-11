package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.Mocket;
import cs3500.pa03.json.JsonUtils;
import cs3500.pa03.json.MessageJson;
import cs3500.pa03.model.AiStackPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.PlayerUnsunkShips;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
  public void testJoin() {
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

    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":\"Jay-Atal\",\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }


  @Test
  public void test2() {

  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
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