package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GameOverTest {
    private Board boardWithPegsAt(BoardType type, int size, Position... pegs) {
        Board board = BoardFactory.create(type, size);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Position p = new Position(row, col);
                if (board.getCell(p) != Cell.INVALID) {
                    board.setCell(p, Cell.EMPTY);
                }
            }
        }
        for (Position p : pegs) {
            board.setCell(p, Cell.PEG);
        }
        return board;
    }

    private static final Position START = new Position(3, 1);
    private static final Position JUMPED = new Position(3, 2);
    private static final Position CENTER = new Position(3, 3);

    @Test
    void gameContinuesWhileValidMoveRemains() {
        SolitaireGame game = new SolitaireGame(BoardFactory.create(BoardType.ENGLISH, 7));

        game.makeMove(new Position(1, 3), CENTER);

        assertFalse(game.isGameOver());
        assertEquals(GameState.IN_PROGRESS, game.getState());
    }

    @Test
    void gameEndsWhenNoValidMovesRemain() {
        SolitaireGame game = new SolitaireGame(boardWithPegsAt(BoardType.ENGLISH, 7, START, JUMPED));
        assertFalse(game.isGameOver());

        game.makeMove(START, CENTER);

        assertTrue(game.isGameOver());
        assertEquals(GameState.OVER, game.getState());
    }

    @Test
    void onePegLeftIsOutstanding() {
        SolitaireGame game = new SolitaireGame(boardWithPegsAt(BoardType.ENGLISH, 7, START, JUMPED));

        game.makeMove(START, CENTER);

        assertEquals(1, game.getPegsLeft());
        assertEquals(Rating.OUTSTANDING, game.getRating());
    }

    @Test
    void twoPegsLeftIsVeryGood() {
        SolitaireGame game = new SolitaireGame(
                boardWithPegsAt(BoardType.ENGLISH, 7, START, JUMPED, new Position(0, 2)));

        game.makeMove(START, CENTER);

        assertTrue(game.isGameOver());
        assertEquals(Rating.VERY_GOOD, game.getRating());
    }

    @Test
    void threePegsLeftIsGood() {
        SolitaireGame game = new SolitaireGame(
                boardWithPegsAt(BoardType.ENGLISH, 7, START, JUMPED, new Position(0, 2), new Position(0, 4)));

        game.makeMove(START, CENTER);

        assertTrue(game.isGameOver());
        assertEquals(Rating.GOOD, game.getRating());
    }

    @Test
    void fourOrMorePegsIsAverage() {
        SolitaireGame game = new SolitaireGame(boardWithPegsAt(BoardType.ENGLISH, 7, START, JUMPED, new Position(0, 2),
                new Position(0, 4), new Position(6, 2), new Position(6, 4)));

        game.makeMove(START, CENTER);

        assertEquals(5, game.getPegsLeft());
        assertEquals(Rating.AVERAGE, game.getRating());
    }

    @Test
    void noMoveCanBeMadeAfterGameOver() {
        SolitaireGame game = new SolitaireGame(boardWithPegsAt(BoardType.ENGLISH, 7, START, JUMPED));
        game.makeMove(START, CENTER);

        boolean moved = game.makeMove(CENTER, START);

        assertFalse(moved);
        assertEquals(Cell.PEG, game.getBoard().getCell(CENTER));
    }

    @Test
    void gameOverDetectedOnHexagonAndDiamondBoards() {
        for (BoardType type : new BoardType[] { BoardType.HEXAGON, BoardType.DIAMOND }) {
            SolitaireGame game = new SolitaireGame(boardWithPegsAt(type, 7, START, JUMPED));

            game.makeMove(START, CENTER);

            assertTrue(game.isGameOver());
            assertEquals(Rating.OUTSTANDING, game.getRating());
        }
    }

    @Test
    void gameIsNotOverWhileOnlyDiagonalMoveRemains() {
        SolitaireGame game = new SolitaireGame(boardWithPegsAt(BoardType.ENGLISH, 7, new Position(2, 2), CENTER));

        assertFalse(game.isGameOver());
        assertEquals(1, game.getValidMoves().size());
    }

    @Test
    void ratingIsNotAvailableBeforeGameIsOver() {
        SolitaireGame game = new SolitaireGame(BoardFactory.create(BoardType.ENGLISH, 7));

        assertThrows(IllegalStateException.class, game::getRating);
    }
}