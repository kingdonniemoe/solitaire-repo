package solitaire.model;

import java.util.Objects;

public final class Position {

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Position step(Direction direction, int distance) {
        return new Position(row + direction.getRowStep() * distance,
                            col + direction.getColStep() * distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position other)) {
            return false;
        }
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Position(" + row + ", " + col + ")";
    }
}