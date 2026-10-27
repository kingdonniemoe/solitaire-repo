package solitaire.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiamondBoardTest {

    @Test
    void topPointIsAHoleButItsNeighborIsNot() {
        Board board = BoardFactory.create(BoardType.DIAMOND, 7);

        assertEquals(Cell.PEG, board.getCell(new Position(0, 3)));
        assertEquals(Cell.INVALID, board.getCell(new Position(0, 2)));
    }
}