package solitaire.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnglishBoardTest {

    @Test
    void defualtBoardHas32Pegs() {
        Board board = BoardFactory.create(BoardType.ENGLISH, 7);

        assertEquals(32, board.countPegs());
    }

    @Test
    void cornerIsNotAHole() {
        Board board = BoardFactory.create(BoardType.ENGLISH, 7);

        assertEquals(Cell.INVALID, board.getCell(new Position(0, 0)));
    }
}