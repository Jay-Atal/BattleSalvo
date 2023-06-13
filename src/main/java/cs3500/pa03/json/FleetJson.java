package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The fleet json to send back to the server for the ships.
 *
 * @param fleet the fleet of ships for the player.
 */
public record FleetJson(@JsonProperty("fleet") List<ShipAdapterJson> fleet) {
}
