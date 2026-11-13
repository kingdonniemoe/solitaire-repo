package solitaire.model;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AutomatedGame extends SolitaireGame {
    private final Random random;

    public AutomatedGame(Board board) {
        this(board, new Random());
    }

    public AutomatedGame(Board board, Random random) {
        super(board);
        this.random = Objects.requireNonNull(random, "random");
    }

    public boolean playNextMove() {
        List<Move> moves = getValidMoves();

        if (moves.isEmpty()) {
            return false;
        }

        Move move = moves.get(random.nextInt(moves.size()));

        return applyMove(move.getFrom(), move.getTo());
    }

}