package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SolitaireGameTest {

    private SolitaireGame newEnglishGame() {
        return new SolitaireGame(BoardFactory.create(BoardType.ENGLISH, 7));
    }

    @Test
    void orthogonalJumpMovesPegAndRemovesJumpedPeg() {
        SolitaireGame game = newEnglishGame();

        boolean moved = game.makeMove(new Position(1, 3), new Position(3, 3));

        assertTrue(moved);
        Board board = game.getBoard();

        assertEquals(Cell.EMPTY, board.getCell((new Position(1, 3))));
        assertEquals(Cell.EMPTY, board.getCell((new Position(2, 3))));
        assertEquals(Cell.PEG, board.getCell(new Position(3, 3)));
        assertEquals(31, game.getPegsLeft());
    }

    @Test
    void diagonalJumpMovesPegAndRemovesJumpedPeg() {
        SolitaireGame game = newEnglishGame();

        Board board = game.getBoard();
        board.setCell(new Position(3, 3), Cell.PEG);
        board.setCell(new Position(4, 4), Cell.EMPTY);

        boolean moved = game.makeMove(new Position(2, 2), new Position(4, 4));

        assertTrue(moved);
        assertEquals(Cell.EMPTY, board.getCell(new Position(2, 2)));
        assertEquals(Cell.EMPTY, board.getCell(new Position(3, 3)));
        assertEquals(Cell.PEG, board.getCell(new Position(4, 4)));
    }

    @Test
    void jumpOverAnEmptyHoleIsRejected() {
        SolitaireGame game = newEnglishGame();
        game.getBoard().setCell(new Position(3, 4), Cell.EMPTY);
        int pegsBefore = game.getPegsLeft();

        boolean moved = game.makeMove(new Position(3, 5), new Position(3, 3));

        assertFalse(moved);
        assertEquals(pegsBefore, game.getPegsLeft());
    }

    @Test
    void destinationNotTwoHolesAwayIsRejected() {
        SolitaireGame game = newEnglishGame();

        assertFalse(game.isValidMove(new Position(3, 2), new Position(3, 3)));
        assertFalse(game.isValidMove(new Position(0, 3), new Position(3, 3)));
        assertFalse(game.isValidMove(new Position(1, 2), new Position(3, 3)));
    }

    @Test
    void jumpIntoNonEmptyHoleIsRejected() {
        SolitaireGame game = newEnglishGame();

        assertFalse(game.isValidMove(new Position(0, 3), new Position(2, 3)));
    }

    @Test
    void newEnglishGameHasExactlyFourValidMoves() {
        SolitaireGame game = newEnglishGame();

        assertEquals(4, game.getValidMoves().size());
    }
}