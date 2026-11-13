package solitaire.controller;

import solitaire.model.AutomatedGame;
import solitaire.model.Board;
import solitaire.model.BoardFactory;
import solitaire.model.BoardType;
import solitaire.model.GameFactory;
import solitaire.model.GameMode;
import solitaire.model.SolitaireGame;
import solitaire.model.Position;
import solitaire.model.ManualGame;

/**
 * 
 * GameController class is used to keep model and ui packages seperated and acts
 * as the interface between the two for communication.
 */
public class GameController {
    public static final String SIZE_ERROR = "Board size must be an odd number from " + Board.MIN_SIZE + " to "
            + Board.MAX_SIZE;
    public static final String INVALID_MOVE = "Invalid move";

    private SolitaireGame game;
    private GameMode mode;
    private Position selected;
    private String message = "";

    public GameController() {
        start(BoardType.ENGLISH, Board.DEFAULT_SIZE, GameMode.MANUAL);
    }

    public boolean newGame(BoardType type, String sizeText) {
        return newGame(type, sizeText, GameMode.MANUAL);
    }

    public boolean newGame(BoardType type, String sizeText, GameMode mode) {
        Integer size = parseSize(sizeText);

        if (size == null || !Board.isValidSize(size)) {
            message = SIZE_ERROR;
            return false;
        }

        start(type, size, mode);
        return true;
    }

    public void handleClick(Position clicked) {
        if (!(game instanceof ManualGame manual) || manual.isGameOver()) {
            return;
        }

        switch (game.getBoard().getCell(clicked)) {
            case PEG -> toggleSelection(clicked);
            case EMPTY -> tryMoveTo(manual, clicked);
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

    public GameMode getMode(){
        return mode;
    }

    private void start(BoardType type, int size, GameMode mode) {
        this.game = GameFactory.create(mode, BoardFactory.create(type,size));
        this.mode = mode;
        selected = null;
        message = "";
    }

    private void toggleSelection(Position peg) {
        selected = peg.equals(selected) ? null : peg;
        message = "";
    }

    private void tryMoveTo(ManualGame manual, Position hole) {
        if (selected == null) {
            return;
        }
        if (manual.makeMove(selected, hole)) {
            selected = null;
            message = manual.isGameOver() ? gameOverMessage() : "";
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

    public boolean autoStep() {
        if (!(game instanceof AutomatedGame automated)) {
            return false;
        }
        boolean moved = automated.playNextMove();
        if (automated.isGameOver()) {
            message = gameOverMessage();
        }
        return moved;
    }

    public boolean randomize() {
        if (!(game instanceof ManualGame manual) || !manual.randomize()) {
            return false;
        }
        selected = null;
        message = "";
        return true;
    }
}