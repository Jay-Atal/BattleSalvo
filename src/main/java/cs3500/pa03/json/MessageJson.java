package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa03.controller.ProxyController;

public record MessageJson(@JsonProperty("method-name") String methodName,
                                   @JsonProperty("arguments") JsonNode arguments) {

}
