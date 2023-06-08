package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;

public record ShipAdapterJSON(@JsonProperty("coord") Coord coord,
                              @JsonProperty("length") int length,
                              @JsonProperty("direction") Direction direction) {

}
