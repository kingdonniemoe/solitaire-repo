package solitaire.model;

import java.util.List;

/**
 * A abstract class that contains a standard grid of cells.
 */

public abstract class Board {

    public static final int MIN_SIZE = 5;
    public static final int MAX_SIZE = 13;
    public static final int DEFAULT_SIZE = 7;

    protected final int size;
    private final Cell[][] grid;

    protected Board(int size) {
        if (!isValidSize(size)) {
            throw new IllegalArgumentException(
                    "Board size must be an odd number from " + MIN_SIZE + " to " + MAX_SIZE + ", but was " + size);
        }
        this.size = size;
        this.grid = new Cell[size][size];
    }

    public static boolean isValidSize(int size) {
        return size >= MIN_SIZE && size <= MAX_SIZE && size % 2 == 1;
    }

    /** Shape specific rule */
    protected abstract boolean isHole(int row, int col);

    void reset() {
        int center = size / 2;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (!isHole(row, col)) {
                    grid[row][col] = Cell.INVALID;
                } else if (row == center && col == center) {
                    grid[row][col] = Cell.EMPTY;
                } else {
                    grid[row][col] = Cell.PEG;
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Position getCenter() {
        return new Position(size / 2, size / 2);
    }

    public boolean isOnGrid(Position p) {
        return p.getRow() >= 0 && p.getRow() < size && p.getCol() >= 0 && p.getCol() < size;
    }

    public Cell getCell(Position p) {
        if (!isOnGrid(p)) {
            return Cell.INVALID;
        }
        return grid[p.getRow()][p.getCol()];
    }

    public int countPegs() {
        int pegs = 0;
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell == Cell.PEG) {
                    pegs++;
                }
            }
        }
        return pegs;
    }

    public List<Direction> getDirections() {
        return List.of(Direction.values());
    }

    void setCell(Position p, Cell cell) {
        if (cell == Cell.INVALID || getCell(p) == Cell.INVALID) {
            throw new IllegalArgumentException("Only holes can be changed, and only to PEG or EMPTY: " + p);
        }
        grid[p.getRow()][p.getCol()] = cell;
    }
}