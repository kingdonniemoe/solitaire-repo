package solitaire.model;

/**
 * 
 * EnglishBoard class that provides overriden method for finding
 * center hole
 */
public class EnglishBoard extends Board {
    EnglishBoard(int size) {
        super(size);
    }
    @Override 
    protected boolean isHole(int row, int col){
        int mid = size / 2;
        return Math.abs(row - mid) <= 1 || Math.abs(col - mid) <= 1;
    }
}