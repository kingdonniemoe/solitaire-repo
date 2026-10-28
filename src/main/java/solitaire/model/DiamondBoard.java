package solitaire.model;

/**
 * 
 * DiamondBoard Class that provides overriden method for finding center hole
 */
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