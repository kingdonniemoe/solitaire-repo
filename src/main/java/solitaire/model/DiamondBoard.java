package solitaire.model;

public class DiamondBoard extends Board {

    DiamondBoard(int size) {
        super(size);
    }

    @Override
    protected boolean isHole(int row, int col) {
        int mid = size / 2;
        return Math.abs(row - mid) + Math.abs(col - mid) <= mid;
    }
}