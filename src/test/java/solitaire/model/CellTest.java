package solitaire.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    @Test
    void hasInvalidEmptyAndPeg() {
        assertArrayEquals(new Cell[] { Cell.INVALID, Cell.EMPTY, Cell.PEG }, Cell.values());
    }
}