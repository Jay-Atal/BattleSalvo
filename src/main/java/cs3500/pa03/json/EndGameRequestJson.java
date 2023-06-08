package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

public record EndGameRequestJson(@JsonProperty("result") GameResult gameResult,
                                 @JsonProperty("reason") String reason) {

}