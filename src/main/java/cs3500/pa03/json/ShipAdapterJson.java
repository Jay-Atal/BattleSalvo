package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;

/**
 * Adapts a ship to the adapted output for the json.
 *
 * @param coord     the starting coord.
 * @param length    the ship length.
 * @param direction the direction of the ship.
 */
public record ShipAdapterJson(@JsonProperty("coord") Coord coord,
                              @JsonProperty("length") int length,
                              @JsonProperty("direction") Direction direction) {

}
