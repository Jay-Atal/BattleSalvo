package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Used to keep track of a method name and it's arguments to be passed through.
 *
 * @param methodName the name of the method.
 * @param arguments the arguments passed through.
 */
public record MessageJson(@JsonProperty("method-name") String methodName,
                                   @JsonProperty("arguments") JsonNode arguments) {

}
