package solitaire.model;

/**
 * Class that creates Board based on type and size.
 */
public final class BoardFactory {

    private BoardFactory() {

    }

    public static Board create(BoardType type, int size) {
        Board board = switch (type) {
            case ENGLISH -> new EnglishBoard(size);
            case HEXAGON -> new HexagonBoard(size);
            case DIAMOND -> new DiamondBoard(size);
        };
        board.reset();
        return board;
    }
}