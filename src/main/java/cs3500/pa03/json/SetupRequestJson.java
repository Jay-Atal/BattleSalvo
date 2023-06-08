package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;

public record SetupRequestJson(@JsonProperty("width") int width,
                               @JsonProperty("height") int height,
                               @JsonProperty("fleet-spec") Map<ShipType, Integer> specifications) {
}

