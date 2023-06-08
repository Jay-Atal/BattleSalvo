package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Coord object.
 *
 * @param x position.
 * @param y position.
 */
public record Coord(@JsonProperty("x") int x,@JsonProperty("y") int y) {

}
