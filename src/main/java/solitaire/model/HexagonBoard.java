package solitaire.model;

public class HexagonBoard extends Board {
    HexagonBoard(int size) {
        super(size);
    }

    @Override
    protected boolean isHole(int row, int col) {
        int mid = size / 2;
        int rowsFromCenter = Math.abs(row - mid);
        int halfWidth = mid - (rowsFromCenter + 1) / 2;
        return Math.abs(col - mid) <= halfWidth;
    }
}