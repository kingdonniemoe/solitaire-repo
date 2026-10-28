package solitaire.controller;

import solitaire.model.Board;
import solitaire.model.BoardFactory;
import solitaire.model.BoardType;
import solitaire.model.SolitaireGame;
import solitaire.model.Position;

/**
 * 
 * GameController class is used to keep model and ui packages seperated and acts as the interface between the two for communication.
 */
public class GameController {
    public static final String SIZE_ERROR = "Board size must be an odd number from " + Board.MIN_SIZE + " to "
            + Board.MAX_SIZE;
    public static final String INVALID_MOVE = "Invalid move";

    private SolitaireGame game;
    private Position selected;
    private String message = "";

    public GameController() {
        start(BoardType.ENGLISH, Board.DEFAULT_SIZE);
    }

    public boolean newGame(BoardType type, String sizeText) {
        Integer size = parseSize(sizeText);
        if (size == null || !Board.isValidSize(size)) {
            message = SIZE_ERROR;
            return false;
        }
        start(type, size);
        return true;
    }

    public void handleClick(Position clicked) {
        if (game.isGameOver()) {
            return;
        }

        switch (game.getBoard().getCell(clicked)) {
            case PEG -> toggleSelection(clicked);
            case EMPTY -> tryMoveTo(clicked);
            case INVALID -> {
            }
        }
    }

    public SolitaireGame getGame() {
        return game;
    }

    public Position getSelected() {
        return selected;
    }

    public String getMessage() {
        return message;
    }

    private void start(BoardType type, int size) {
        game = new SolitaireGame(BoardFactory.create(type, size));
        selected = null;
        message = "";
    }

    private void toggleSelection(Position peg) {
        selected = peg.equals(selected) ? null : peg;
        message = "";
    }

    private void tryMoveTo(Position hole) {
        if (selected == null) {
            return;
        }
        if (game.makeMove(selected, hole)) {
            selected = null;
            message = game.isGameOver() ? gameOverMessage() : "";
        } else {
            message = INVALID_MOVE;
        }
    }

    private String gameOverMessage() {
        int pegs = game.getPegsLeft();
        return "Game Over: " + pegs + (pegs == 1 ? " peg" : " pegs") + " left. Rating: " + game.getRating().getLabel();
    }

    private static Integer parseSize(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}