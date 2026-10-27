package solitaire.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HexagonBoardTest {

    @Test
    void spotNextToTheTopCornerIsAHole() {
        Board board = BoardFactory.create(BoardType.HEXAGON, 7);

        assertEquals(Cell.PEG, board.getCell(new Position(1, 1)));
    }
}