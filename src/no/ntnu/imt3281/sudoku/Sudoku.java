package no.ntnu.imt3281.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;

/**
 * Sudoku class is a representation of a Sudoku board with the standard 9*9 grid
 * layout.
 */
public class Sudoku {
    private List<List<Cell>> mSudokuBoard;

    public static final int ROW_SIZE = 9;
    public static final int COL_SIZE = 9;
    public static final int GRID_COUNT = 9;
    public static final int EMPTY_CELL = -1;

    /**
     * Representation of a cell in the sudoku board, containing both a value and a
     * bool indicating if it is locked.
     */
    protected static class Cell {
        private int mValue = EMPTY_CELL;
        private boolean mIsLocked = false;

        public int getValue() {
            return mValue;
        }

        public boolean isLocked() {
            return mIsLocked;
        }

        /**
         * Updates the value of the cell if it is not locked
         *
         * @param value The desired value of the cell
         *
         * @exception LockedCellException Throws LockedCellException if you try to
         *                                modify it when it is locked.
         *
         * @see no.ntnu.imt3281.sudoku.LockedCellException
         */
        private void setValue(int value) {
            if (mIsLocked) {
                throw new LockedCellException();
            }

            mValue = value;
        }

        /**
         * Locks a cell if the cell has been given a value.
         */
        private void lock() {
            if (mValue != EMPTY_CELL) {
                mIsLocked = true;
            }
        }
    }

    /**
     * Creates a new entirely empty Sudoku board.
     */
    public Sudoku() {
        mSudokuBoard = new ArrayList<>();
        for (int row = 0; row < ROW_SIZE; row++) {
            mSudokuBoard.add(new ArrayList<>());
            for (int col = 0; col < COL_SIZE; col++) {
                mSudokuBoard.get(row).add(new Cell());
            }
        }
    }

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

                // We are ignoring empty cells so we can reuse the validation logic in addNumber
                // (which don't accept EMPTY_CELL).
                if (value != EMPTY_CELL) {
                    board.addNumber(row, col, value);
                }
            }
        }

        return board;
    }

    /**
     * Saves the sudoku board to the file specified by the filepath.
     *
     * @param board    The board to save
     * @param filepath The path to where the file should be saved.
     *
     * @exception IOException Throws IOException on IO errors.
     */
    public static void saveSudokuToFile(Sudoku board, final Path filepath) throws IOException {
        try (var writer = new OutputStreamWriter(new FileOutputStream(filepath.toFile()), StandardCharsets.UTF_8)) {
            for (int row = 0; row < ROW_SIZE; row++) {
                for (int col = 0; col < COL_SIZE; col++) {
                    var value = board.mSudokuBoard.get(row).get(col).getValue();
                    var locked = board.mSudokuBoard.get(row).get(col).isLocked() ? 1 : 0;
                    writer.write(String.format("%d %d, ", value, locked));
                }
                writer.write("\n");
            }
        }
    }

    /**
     * Loads a sudoku board from the file specified by filepath.
     *
     * @param filepath The filepath to the file to load the sudoku board from.
     *
     * @return A new sudoku board containing the configuration in the file.
     *
     * @exception InvalidSudokuFileException Throws InvalidSudokuFileException in
     *                                       the case where the sudoku file is not
     *                                       formatted properly, or is incomplete
     *                                       (for example, missing a row or column).
     *
     * @exception IOException                Throws IOException on IO errors.
     */
    public static Sudoku loadSudokuFromFile(final Path filepath) throws IOException {
        var board = new Sudoku();

        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath.toFile()), "UTF-8"))) {
            int row = 0;
            String line;

            while ((line = reader.readLine()) != null && row < Sudoku.ROW_SIZE) {
                var rowList = new ArrayList<>(Arrays.asList(line.split("[,\r ]")));
                rowList.removeIf(item -> item == null || "".equals(item));

                if (rowList.size() < Sudoku.COL_SIZE * 2) {
                    throw new InvalidSudokuFileException();
                }

                for (int col = 0, i = 0; i < Sudoku.COL_SIZE * 2; col++, i += 2) {
                    int value = Integer.parseInt(rowList.get(i));
                    int locked = Integer.parseInt(rowList.get(i + 1));

                    if (value != -1) {
                        board.addNumber(row, col, value);
                    }

                    // This is an bug waiting to happen, because we don't check here that the value
                    // is != -1, which allows people to lock empty cells.
                    if (locked == 1) {
                        board.mSudokuBoard.get(row).get(col).lock();
                    }
                }

                row++;
            }

            if (row < Sudoku.ROW_SIZE) {
                throw new InvalidSudokuFileException();
            }
        }

        return board;
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
        return mSudokuBoard.get(row).get(col).getValue();
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
        mSudokuBoard.get(row).get(col).setValue(value);
    }

    /**
     * Checks whether setting the element in the sudoku board at the position
     * indicated by row and col to equal value would be a valid move, and if so,
     * calls setElement to do so.
     *
     * @param row   The row containing the element.
     * @param col   The column containing the element.
     * @param value The value we're trying to set the element to be.
     *
     * @exception IllegalArgumentException Throws IllegalArgumentException if value
     *                                     is not in range [1-9]
     *
     * @exception BadNumberException       Throws BadNumberException if value
     *                                     already exists within the row, column, or
     *                                     sub grid it is entered into.
     */
    public void addNumber(int row, int col, int value) {

        // Check if element is locked
        // Check that value is a number from 1 to 9
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException();
        }

        Class<?>[] iterators = { RowIterator.class, ColumnIterator.class, SubGridIterator.class };
        int[] indices = { row, col, (row / 3) * 3 + (col / 3) };

        for (int i = 0; i < iterators.length; i++) {
            var it = iterator(iterators[i], indices[i]);
            while (it.hasNext()) {
                var val = it.peek();
                if (val == value) {
                    throw new BadNumberException(it);
                }
                it.next();
            }
        }

        setElement(row, col, value);
    }

    /**
     * Locks all the numbers that has already been given a legal value, making it
     * impossible to change them
     */
    public void lockNumbers() {
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
                mSudokuBoard.get(row).get(col).lock();
            }
        }
    }

    /**
     * Checks if a specific number in the sudoku board is locked.
     *
     * @param row The row containing the number to check.
     *
     * @param col The column containing the number to check.
     *
     * @return true if the number is locked, false otherwise.
     */
    public boolean isNumberLocked(int row, int col) {
        return mSudokuBoard.get(row).get(col).isLocked();
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
     * @exception IllegalArgumentException Throws IllegalArgumentException if an
     *                                     unsupported type is supplied. Type must
     *                                     be: RowIterator, ColumnIterator or
     *                                     SubGridIterator. Throws
     *                                     IllegalArgumentException if an
     *                                     unsupported type is supplied.
     */
    protected SudokuIterator iterator(Class<?> type, int value) {
        if (type == RowIterator.class) {
            return new RowIterator(this, value);
        } else if (type == ColumnIterator.class) {
            return new ColumnIterator(this, value);
        } else if (type == SubGridIterator.class) {
            return new SubGridIterator(this, value);
        }

        throw new IllegalArgumentException("Class must be Row-, Column- or SubGrid-Iterator");
    }

    /**
     * Changes all existing values into new values, while still maintaining the
     * layout of the numbers on the board.
     *
     * The number of instances of a value and their placements are kept the same,
     * but the actual "value" is changed. For example: If there were three 4's on
     * the board before randomization, and all 4's are changed to 2's, then there
     * will still be three 2's on the table, in the same positions that the 4's were
     * in the original table.
     *
     * Also, all numbers are changed into a different one, to ensure that the
     * legality of the board is intact.
     */
    protected void randomizeAllExistingElements() {
        var values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(values);

        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                var value = getElement(row, col);
                if (value != Sudoku.EMPTY_CELL) {
                    setElement(row, col, values.get(value - 1));
                }
            }
        }
    }
}
