package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void newBoardHasAnEmptyCenter() {
        Board board = BoardFactory.create(BoardType.ENGLISH, 7);

        assertEquals(Cell.EMPTY, board.getCell(board.getCenter()));
    }

    @Test
    void spotsOffTheGridAreInvalid() {
        Board board = BoardFactory.create(BoardType.ENGLISH, 7);

        assertEquals(Cell.INVALID, board.getCell(new Position(-1, 0)));
    }
}