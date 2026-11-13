package solitaire.model;

public final class GameFactory {
    private GameFactory() {

    }

    public static SolitaireGame create(GameMode mode, Board board) {
        return switch (mode) {
            case MANUAL -> new ManualGame(board);
            case AUTOMATED -> new AutomatedGame(board);
        };
    }
}