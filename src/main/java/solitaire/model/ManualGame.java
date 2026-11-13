package solitaire.model;

public class ManualGame extends SolitaireGame {
    public ManualGame(Board board) {
        super(board);
    }

    public boolean makeMove(Position from, Position to) {
        return applyMove(from, to);
    }
}