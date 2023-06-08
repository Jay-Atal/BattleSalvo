package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

public record VolleyJSON(@JsonProperty("coordinates") List<Coord> shots) {

}
