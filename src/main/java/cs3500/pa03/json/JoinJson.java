package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JoinJson(@JsonProperty("name") String name,
                       @JsonProperty("game-type") String gameType) {
}
