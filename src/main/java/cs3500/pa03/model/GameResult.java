package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representing the final result of the game as either a Win, Loss or Tie.
 */
public enum GameResult {
  @JsonProperty("WIN") WIN,
  @JsonProperty("LOSE") LOSE,
  @JsonProperty("DRAW") DRAW
}
