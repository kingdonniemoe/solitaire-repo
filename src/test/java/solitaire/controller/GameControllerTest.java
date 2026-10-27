package solitaire.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import solitaire.model.Board;
import solitaire.model.BoardType;
import solitaire.model.Cell;
import solitaire.model.DiamondBoard;
import solitaire.model.EnglishBoard;
import solitaire.model.Move;
import solitaire.model.Position;
import solitaire.model.SolitaireGame;

class GameControllerTest {
    private static final Position CENTER = new Position(3, 3);

    private void playUntilGameOver(GameController controller) {
        SolitaireGame game = controller.getGame();
        while (!game.isGameOver()) {
            Move move = game.getValidMoves().get(0);
            controller.handleClick(move.getFrom());
            controller.handleClick(move.getTo());
        }
    }

    @Test
    void validSizeStartsNewGameOfSize() {
        GameController controller = new GameController();

        assertTrue(controller.newGame(BoardType.DIAMOND, "9"));

        assertInstanceOf(DiamondBoard.class, controller.getGame().getBoard());
        assertEquals(9, controller.getGame().getBoard().getSize());
    }

    @Test
    void evenSizeShowsErrorAndKeepsCurrentGame() {
        GameController controller = new GameController();
        SolitaireGame before = controller.getGame();

        assertFalse(controller.newGame(BoardType.ENGLISH, "8"));

        assertEquals(GameController.SIZE_ERROR, controller.getMessage());
        assertSame(before, controller.getGame());
    }

    @Test
    void sizeBelowMinShowsError() {
        GameController controller = new GameController();

        assertFalse(controller.newGame(BoardType.ENGLISH, "3"));
        assertEquals(GameController.SIZE_ERROR, controller.getMessage());
    }

    @Test
    void sizeAboveMaxShowsError() {
        GameController controller = new GameController();

        assertFalse(controller.newGame(BoardType.ENGLISH, "15"));
        assertEquals(GameController.SIZE_ERROR, controller.getMessage());
    }

    @Test
    void decimalOrEmptySizeShowsError() {
        GameController controller = new GameController();

        for (String bad : new String[] { "abc", "7.5", "" }) {
            assertFalse(controller.newGame(BoardType.ENGLISH, bad));
            assertEquals(GameController.SIZE_ERROR, controller.getMessage());
        }
    }

    @Test
    void startsWithEnglishBoardOfSize7() {
        GameController controller = new GameController();

        assertInstanceOf(EnglishBoard.class, controller.getGame().getBoard());
        assertEquals(7, controller.getGame().getBoard().getSize());
    }

    @Test
    void newGameAfterGameOverGivesFreshBoard() {
        GameController controller = new GameController();
        playUntilGameOver(controller);

        controller.newGame(BoardType.ENGLISH, "7");

        assertFalse(controller.getGame().isGameOver());
        assertEquals(32, controller.getGame().getPegsLeft());
        assertEquals("", controller.getMessage());
    }

    @Test
    void clickignPegSelectsIt() {
        GameController controller = new GameController();

        controller.handleClick(new Position(1, 3));

        assertEquals(new Position(1, 3), controller.getSelected());
    }

    @Test
    void selectingPegThenHoleMakesJump() {
        GameController controller = new GameController();

        controller.handleClick(new Position(1, 3));
        controller.handleClick(CENTER);

        assertEquals(Cell.PEG, controller.getGame().getBoard().getCell(CENTER));
        assertEquals(31, controller.getGame().getPegsLeft());
        assertNull(controller.getSelected());
    }

    @Test
    void invalidDestinationKeepsPegSelectedAndShowsMessage() {
        GameController controller = new GameController();

        controller.handleClick(new Position(0, 3));
        controller.handleClick(CENTER);

        assertEquals(new Position(0, 3), controller.getSelected());
        assertEquals(GameController.INVALID_MOVE, controller.getMessage());
        assertEquals(32, controller.getGame().getPegsLeft());
    }

    @Test
    void clickingOutsideBoardDoesNothing() {
        GameController controller = new GameController();

        controller.handleClick(new Position(0, 0));
        controller.handleClick(new Position(-1, 0));

        assertNull(controller.getSelected());
    }

    @Test
    void clickingEmptyHoleWithNothingSelectedDoesNothing() {
        GameController controller = new GameController();

        controller.handleClick(CENTER);

        assertNull(controller.getSelected());
        assertEquals(32, controller.getGame().getPegsLeft());
    }

    @Test
    void clickingAnotherPegChangesSelection() {
        GameController controller = new GameController();

        controller.handleClick(new Position(1, 3));
        controller.handleClick(new Position(3, 1));

        assertEquals(new Position(3, 1), controller.getSelected());
    }

    @Test
    void clickingSelectedPegAgainCancelsSelection() {
        GameController controller = new GameController();

        controller.handleClick(new Position(1, 3));
        controller.handleClick(new Position(1, 3));

        assertNull(controller.getSelected());
    }

    @Test
    void finalMoveShowsGameOverWithRating() {
        GameController controller = new GameController();

        playUntilGameOver(controller);

        String rating = controller.getGame().getRating().getLabel();
        assertTrue(controller.getMessage().startsWith("Game Over"));
        assertTrue(controller.getMessage().endsWith("Rating: " + rating));
    }

    @Test
    void clicksAreIgnoredAfterGameOver() {
        GameController controller = new GameController();
        playUntilGameOver(controller);
        Board board = controller.getGame().getBoard();
        Position remainingPeg = findAnyPeg(board);

        controller.handleClick(remainingPeg);

        assertNull(controller.getSelected());
    }

    private Position findAnyPeg(Board board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Position p = new Position(row, col);
                if (board.getCell(p) == Cell.PEG) {
                    return p;
                }
            }
        }
        throw new AssertionError("No Peg left on the board");
    }
}