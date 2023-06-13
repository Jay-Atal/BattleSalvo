package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * Volley representing a set of shots.
 *
 * @param shots the shots passed through.
 */
public record VolleyJson(@JsonProperty("coordinates") List<Coord> shots) {

}
