package solitaire.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 * Class that holds the boards and is storing the game
 */
public class SolitaireGame {
    private final Board board;

    public SolitaireGame(Board board) {
        this.board = Objects.requireNonNull(board, "board");
    }

    public Board getBoard() {
        return board;
    }

    public int getPegsLeft() {
        return board.countPegs();
    }

    public boolean isValidMove(Position from, Position to) {
        return findMove(from, to) != null;
    }

    public boolean makeMove(Position from, Position to) {
        Move move = findMove(from, to);
        if (move == null) {
            return false;
        }
        board.setCell(move.getFrom(), Cell.EMPTY);
        board.setCell(move.getOver(), Cell.EMPTY);
        board.setCell(move.getTo(), Cell.PEG);
        return true;
    }

    public List<Move> getValidMoves() {
        List<Move> moves = new ArrayList<>();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Position from = new Position(row, col);
                if (board.getCell(from) != Cell.PEG) {
                    continue;
                }
                for (Direction direction : board.getDirections()) {
                    Move move = findMove(from, from.step(direction, 2));
                    if (move != null) {
                        moves.add(move);
                    }
                }
            }
        }
        return moves;
    }

    private Move findMove(Position from, Position to) {
        if (board.getCell(from) != Cell.PEG || board.getCell(to) != Cell.EMPTY) {
            return null;
        }
        for (Direction direction : board.getDirections()) {
            if (from.step(direction, 2).equals(to)) {
                Position over = from.step(direction, 1);
                return board.getCell(over) == Cell.PEG ? new Move(from, over, to) : null;
            }
        }
        return null;
    }

    public GameState getState() {
        return getValidMoves().isEmpty() ? GameState.OVER : GameState.IN_PROGRESS;
    }

    public boolean isGameOver() {
        return getState() == GameState.OVER;
    }

    public Rating getRating() {
        if (!isGameOver()) {
            throw new IllegalStateException("The game is not over yet");
        }
        return Rating.fromPegsLeft(getPegsLeft());
    }
}