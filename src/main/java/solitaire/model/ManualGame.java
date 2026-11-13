package solitaire.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ManualGame extends SolitaireGame {

    private static final int MAX_SHUFFLES = 1000;

    private final Random random;

    public ManualGame(Board board) {
        this(board, new Random());
    }

    public ManualGame(Board board, Random random) {
        super(board);
        this.random = Objects.requireNonNull(random, "random");
    }

    public boolean makeMove(Position from, Position to) {
        return applyMove(from, to);
    }

    public boolean randomize() {
        if (isGameOver()) {
            return false;
        }
        Board board = getBoard();
        int pegs = board.countPegs();
        List<Position> holes = board.getHoles();
        for (int attempt = 0; attempt < MAX_SHUFFLES; attempt++) {
            Collections.shuffle(holes, random);
            for (int i = 0; i < holes.size(); i++) {
                board.setCell(holes.get(i), i < pegs ? Cell.PEG : Cell.EMPTY);
            }
            if (!isGameOver()) {
                break;
            }
        }
        return true;
    }
}