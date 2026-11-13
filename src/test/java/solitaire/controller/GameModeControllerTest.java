package solitaire.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import solitaire.model.AutomatedGame;
import solitaire.model.BoardType;
import solitaire.model.GameMode;
import solitaire.model.ManualGame;
import solitaire.model.Position;

class GameModeControllerTest {
    private GameController automatedController() {
        GameController controller = new GameController();
        controller.newGame(BoardType.ENGLISH, "7", GameMode.AUTOMATED);
        return controller;
    }

    @Test
    void controllerStartsInManual() {
        GameController controller = new GameController();

        assertEquals(GameMode.MANUAL, controller.getMode());
        assertInstanceOf(ManualGame.class, controller.getGame());
    }

    @Test
    void newGameInAutomateCreatesAutoGame() {
        GameController controller = new GameController();

        assertTrue(controller.newGame(BoardType.HEXAGON, "9", GameMode.AUTOMATED));

        assertEquals(GameMode.AUTOMATED, controller.getMode());
        assertInstanceOf(AutomatedGame.class, controller.getGame());
        assertEquals(9, controller.getGame().getBoard().getSize());
    }

    @Test
    void autoStepMakesMove() {
        GameController controller = automatedController();

        assertTrue(controller.autoStep());

        assertEquals(31, controller.getGame().getPegsLeft());
    }

    @Test
    void autoStepDoesNothingInManualGame() {
        GameController controller = new GameController();

        assertFalse(controller.autoStep());

        assertEquals(32, controller.getGame().getPegsLeft());
    }

    @Test
    void clicksAreIgnoredInAutomatedGame() {
        GameController controller = automatedController();

        controller.handleClick(new Position(1, 3));
        controller.handleClick(new Position(3, 3));

        assertNull(controller.getSelected());

        assertEquals(32, controller.getGame().getPegsLeft());
    }

    @Test
    void randomizeKeepsPegCountAndClearsSelections() {
        GameController controller = new GameController();
        controller.handleClick(new Position(1, 3));

        assertTrue(controller.randomize());

        assertNull(controller.getSelected());
        assertEquals(32, controller.getGame().getPegsLeft());
        assertFalse(controller.getGame().isGameOver());
    }

    @Test
    void randomizeDoesNothingInAutomatedGame() {
        GameController controller = automatedController();

        assertFalse(controller.randomize());
    }
}
