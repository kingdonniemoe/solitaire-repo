package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class BoardTypeTest {
    @Test
    void hasEnglishHexagonAndDiamond() {
        assertArrayEquals(new BoardType[] { BoardType.ENGLISH, BoardType.HEXAGON, BoardType.DIAMOND },
                BoardType.values());
    }
}