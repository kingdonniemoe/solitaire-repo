package solitaire.ui;

import java.util.function.Consumer;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import solitaire.model.Board;
import solitaire.model.Cell;
import solitaire.model.Position;

/**
 * Draws a board
 */
public final class BoardView extends Pane {

    private static final double BOARD_PIXELS = 520;

    private static final Color PEG = Color.web("#2b2b2b");
    private static final Color PEG_EDGE = Color.web("#111111");
    private static final Color EMPTY = Color.WHITE;
    private static final Color HOLE_EDGE = Color.web("#777777");
    private static final Color SELECTED = Color.web("#2f6fde");
    private static final Color SELECTED_RING = Color.web("#f2b705");

    private final Consumer<Position> onCellClicked;

    public BoardView(Consumer<Position> onCellClicked) {
        this.onCellClicked = onCellClicked;
        setPrefSize(BOARD_PIXELS, BOARD_PIXELS);
        setMinSize(BOARD_PIXELS, BOARD_PIXELS);
        setMaxSize(BOARD_PIXELS, BOARD_PIXELS);
    }

    public void render(Board board, Position selected) {
        getChildren().clear();
        int size = board.getSize();
        double spacing = BOARD_PIXELS / size;
        double radius = spacing * 0.36;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Position position = new Position(row, col);
                Cell cell = board.getCell(position);
                if (cell == Cell.INVALID) {
                    continue;
                }
                Circle hole = new Circle(col * spacing + spacing / 2, row * spacing + spacing / 2, radius);
                style(hole, cell, position.equals(selected));
                hole.setCursor(Cursor.HAND);
                hole.setOnMouseClicked(event -> onCellClicked.accept(position));
                getChildren().add(hole);
            }
        }
    }

    private void style(Circle hole, Cell cell, boolean isSelected) {
        if (cell == Cell.PEG) {
            hole.setFill(isSelected ? SELECTED : PEG);
            hole.setStroke(isSelected ? SELECTED_RING : PEG_EDGE);
            hole.setStrokeWidth(isSelected ? 4 : 1.5);
        } else {
            hole.setFill(EMPTY);
            hole.setStroke(HOLE_EDGE);
            hole.setStrokeWidth(1.5);
        }
    }
}