package no.ntnu.imt3281.sudoku;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class Sudoku {
    private List<List<Integer>> mSudokuBoard;

    public static final int ROW_SIZE = 9;
    public static final int COL_SIZE = 9;
    public static final int GRID_COUNT = 9;
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

    /**
     * Checks whether setting the element in the sudoku board at the position
     * indicated by row and col to equal value would be a valid move, and if so,
     * calls setElement to do so.
     *
     * @param row   The row containing the element.
     * @param col   The column containing the element.
     * @param value The value we're trying to set the element to be.
     */
    public void addNumber(int row, int col, int value) {

        // Check if element is locked
        // Check that value is a number from 1 to 9
        if (value < 1 || value > 9) {
            throw new BadNumberException("Value is not a number from 1 to 9");
        }

        // Check that row is a number from 0 to 8
        if (row < 0 || row >= ROW_SIZE) {
            throw new BadNumberException("row is not a value from 0 to 8");
        }

        // Check that col is a number from 0 to 8
        if (col < 0 || col >= COL_SIZE) {
            throw new BadNumberException("col is not a value from 0 to 8");
        }

        // Check whether the number already exists in this row
        for (int i = 0; i < ROW_SIZE; ++i) {
            if (getElement(i, col) == value) {
                throw new BadNumberException("Number already exists in this row");
            }
        }

        // Check whether the number already exists in this column
        for (int j = 0; j < COL_SIZE; ++j) {
            if (getElement(row, j) == value) {
                throw new BadNumberException("Number already exists in this column");
            }
        }

        // Check whether the number already exists in this 3x3 subgrid
        int rowOffset = row / 3;
        int colOffset = col / 3;
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 3; ++l) {
                if (getElement(k + rowOffset * 3, l + colOffset * 3) == value) {
                    throw new BadNumberException("Number already exists in this 3x3 subgrid");
                }
            }
        }

        // If all tests are good, set the element's value to equal param value
        setElement(row, col, value);
    }

    /**
     * Gets an iterator to sudoku board. The iteration strategy is indicated by the
     * type parameter supplied.
     *
     * @param type  The type indicating the iteration strategy to be used. Must be
     *              RowIterator, ColumnIterator or SubGridIterator.
     *
     * @param value Indicates the selection of the iteration. If type is RowIterator
     *              this is the row you wish to iterate over, in case of
     *              ColumnIterator it is the column to iterate over, lastly in case
     *              of SubGrid it is the id of the sub grid to iterate over.
     *
     * @return An iterator using the strategy indicated by the type parameter.
     *
     * @exception Throws IllegalArgumentException if an unsupported type is
     *                   supplied. Type must be: RowIterator, ColumnIterator or
     *                   SubGridIterator. Throws IllegalArgumentException if an
     *                   unsupported type is supplied.
     */
    protected SudokuIterator iterator(Class<?> type, int value) {
        if (type == RowIterator.class)
            return new RowIterator(mSudokuBoard, value);
        else if (type == ColumnIterator.class)
            return new ColumnIterator(mSudokuBoard, value);
        else if (type == SubGridIterator.class)
            return new SubGridIterator(mSudokuBoard, value);

        throw new IllegalArgumentException("Class must be Row-, Column- or SubGrid-Iterator");
    }
}
