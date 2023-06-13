package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The json for joining the server.
 *
 * @param name username.
 * @param gameType the type of the game being run.
 */
public record JoinJson(@JsonProperty("name") String name,
                       @JsonProperty("game-type") String gameType) {
}
