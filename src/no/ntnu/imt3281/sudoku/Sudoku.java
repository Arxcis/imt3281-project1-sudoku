package no.ntnu.imt3281.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    private List<List<Integer>> mSudokuBoard;

    /**
     * Creates a new sudoku board from the given board
     * 
     * @param board The new board
     */
    public Sudoku(final List<List<Integer>> board) {
        mSudokuBoard = new ArrayList<>();
        for (var row : board) {
            mSudokuBoard.add(new ArrayList<>(row));
        }
    }

    /**
     * Gets the element in the sudoku board located at the position indicated by row
     * and col.
     * 
     * @param row The row containing the element.
     * @param col The column containing the element.
     * @return The element located at the position indicated by row and col.
     */
    protected int getElement(int row, int col) {
        return mSudokuBoard.get(row).get(col);
    }

    /**
     * Sets the element in the sudoku board located at the position indicated by row
     * and col.
     * 
     * @param row   The row containing the element.
     * @param col   The column containing the element.
     * @param value The new value of the element.
     */
    protected void setElement(int row, int col, int value) {
        mSudokuBoard.get(row).set(col, value);
    }
}
