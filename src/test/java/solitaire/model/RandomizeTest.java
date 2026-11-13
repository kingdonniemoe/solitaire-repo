package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class RandomizeTest {
    private ManualGame seededGame(BoardType type, long seed) {
        return new ManualGame(BoardFactory.create(type, 7), new Random(seed));
    }

    private List<Cell> snapshot(Board board) {
        List<Cell> cells = new ArrayList<>();
        for (Position hole : board.getHoles()) {
            cells.add(board.getCell(hole));
        }
        return cells;
    }

    @ParameterizedTest
    @EnumSource(BoardType.class)
    void randomizeKeepsNumberOfPegsAndHoles(BoardType type) {
        ManualGame game = seededGame(type, 5);
        int pegsBefore = game.getPegsLeft();
        int holesBefore = game.getBoard().getHoles().size();

        assertTrue(game.randomize());

        assertEquals(pegsBefore, game.getPegsLeft());
        assertEquals(holesBefore, game.getBoard().getHoles().size());
    }

    @Test
    void randomizeNeverPutsPegOutOfBounds() {
        ManualGame game = seededGame(BoardType.ENGLISH, 5);

        game.randomize();

        assertEquals(Cell.INVALID, game.getBoard().getCell(new Position(0, 0)));
    }

    @Test
    void randomizeChangesArrangment() {
        ManualGame game = seededGame(BoardType.ENGLISH, 5);
        List<Cell> before = snapshot(game.getBoard());

        game.randomize();

        assertNotEquals(before, snapshot(game.getBoard()));
    }

    @Test
    void randomizeLeavesOneLegalMove() {
        for (long seed = 0; seed < 50; seed++) {
            ManualGame game = seededGame(BoardType.ENGLISH, seed);
            game.makeMove(new Position(1, 3), new Position(3, 3));

            game.randomize();

            assertFalse(game.isGameOver());
        }
    }
}