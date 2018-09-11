package no.ntnu.imt3281.sudoku;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class Sudoku {
    private List<List<Integer>> mSudokuBoard;

    public static final int ROW_SIZE = 9;
    public static final int COL_SIZE = 9;
    public static final int EMPTY_CELL = -1;

    /**
     * Creates a new Sudoku board constructed from the supplied json string.
     *
     * @param jsonString String containing the
     * @return a new Sudoku board constructed from the supplied json string.
     */
    public static Sudoku loadSudokuFromJson(final String jsonString) {
        var board = new Sudoku();
        var array = new JSONArray(jsonString);

        for (int row = 0; row < array.length(); row++) {
            var jsonRow = array.getJSONArray(row);

            for (int col = 0; col < jsonRow.length(); col++) {
                var value = jsonRow.getInt(col);
                if (value != EMPTY_CELL) {
                    board.addNumber(row, col, value);
                }
            }
        }

        return board;
    }


    /**
     * Creates a new entirely empty Sudoku board. 
     */
    public Sudoku() {
        mSudokuBoard = new ArrayList<>();
        for (int row = 0; row < ROW_SIZE; row++) {
            mSudokuBoard.add(new ArrayList<>());
            for (int col = 0; col < COL_SIZE; col++) {
                mSudokuBoard.get(row).add(EMPTY_CELL);
            }
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
