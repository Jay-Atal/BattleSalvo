package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

/**
 * Representing the endgame request from the server.
 *
 * @param gameResult the result of the game.
 * @param reason the reason for the result.
 */
public record EndGameRequestJson(@JsonProperty("result") GameResult gameResult,
                                 @JsonProperty("reason") String reason) {

}