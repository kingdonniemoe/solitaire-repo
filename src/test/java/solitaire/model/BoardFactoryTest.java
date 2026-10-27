package solitaire.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

    @Test
    void englishTypeCreatesAnEnglishBoard() {
        Board board = BoardFactory.create(BoardType.ENGLISH, 7);

        assertInstanceOf(EnglishBoard.class, board);
        assertEquals(7, board.getSize());
    }

    @Test
    void evenSizeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> BoardFactory.create(BoardType.ENGLISH, 8));
    }
}