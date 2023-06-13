package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;

/**
 * The request for setting up the player.
 *
 * @param width of the board.
 * @param height of the board.
 * @param specifications of the board representing the ships.
 */
public record SetupRequestJson(@JsonProperty("width") int width,
                               @JsonProperty("height") int height,
                               @JsonProperty("fleet-spec") Map<ShipType, Integer> specifications) {
}

