package util;

/**
 * Directions in a map with their given vector, no diagonals.
 */
public enum Direction {
    UP(0, -1), RIGHT(1, 0), LEFT(-1, 0), DOWN(0, 1),
    UP_RIGHT(1, -1), UP_LEFT(-1, -1), DOWN_RIGHT(1, 1), DOWN_LEFT(-1, 1);

    final Coordinate vector;

    Direction(int x, int y) {
        this.vector = new Coordinate(x, y);
    }
}