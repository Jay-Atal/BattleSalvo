package cs3500.pa03.model;

/**
 * Represents a Ship object.
 *
 * @param shipType  the type of ship.
 * @param coord     the starting position of the ship.
 * @param direction the direction the ship goes in.
 */
public record Ship(ShipType shipType, Coord coord, Direction direction) {
}
