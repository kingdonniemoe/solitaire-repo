package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class AutomatedGameTest {
    private AutomatedGame seededGame(long seed) {
        return new AutomatedGame(BoardFactory.create(BoardType.ENGLISH, 7), new Random(seed));
    }

    @Test
    void eachAutomatedMoveRemovesExactlyOnePeg() {
        AutomatedGame game = seededGame(5);

        boolean moved = game.playNextMove();

        assertTrue(moved);
        assertEquals(31, game.getPegsLeft());
    }

    @Test
    void firstAutomatedMoveOnEnglishLandsInCenter() {
        AutomatedGame game = seededGame(5);

        game.playNextMove();

        assertEquals(Cell.PEG, game.getBoard().getCell(game.getBoard().getCenter()));
    }

    @Test
    void sameSeedPlaysTheSameGame() {
        AutomatedGame first = seededGame(6);
        AutomatedGame second = seededGame(6);

        while (first.playNextMove()) {
            second.playNextMove();
        }

        assertTrue(second.isGameOver());
        assertEquals(first.getPegsLeft(), second.getPegsLeft());
    }

    @ParameterizedTest
    @EnumSource(BoardType.class)
    void automatedGamePlaysUntilNoMoreMoves(BoardType type) {
        AutomatedGame game = new AutomatedGame(BoardFactory.create(type, 7), new Random(1));

        int moves = 0;

        while (game.playNextMove()) {
            moves++;
        }

        assertTrue(game.isGameOver());
        assertTrue(moves > 0);
        assertEquals(Rating.fromPegsLeft(game.getPegsLeft()), game.getRating());
    }

    @Test
    void noMoveIsMadeAfterAutoGameIsOver() {
        AutomatedGame game = seededGame(5);

        while (game.playNextMove()) {

        }
        int pegsAtEnd = game.getPegsLeft();

        boolean moved = game.playNextMove();

        assertFalse(moved);
        assertEquals(pegsAtEnd, game.getPegsLeft());
    }

    @Test
    void factoryCreatesGameClassForEachMode() {
        Board board = BoardFactory.create(BoardType.ENGLISH, 7);

        assertInstanceOf(ManualGame.class, GameFactory.create(GameMode.MANUAL, board));
        assertInstanceOf(AutomatedGame.class, GameFactory.create(GameMode.AUTOMATED, board));
    }
}