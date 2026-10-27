package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class GameStateTest {

    @Test
    void hasInProgressAndOver() {
        assertArrayEquals(new GameState[] { GameState.IN_PROGRESS, GameState.OVER }, GameState.values());
    }
}