package no.ntnu.imt3281.sudoku;

import java.util.NoSuchElementException;

/**
 * Iterator used to iterate through a column in a sudoku board.
 */
public class ColumnIterator implements SudokuIterator {
    private final Sudoku mCollection;
    private int mRow;
    private int mColumn;

    /**
     * Creates a column iterator that will iterate through the selected column in
     * the supplied sudoku board.
     *
     * @param sudoku The sudoku board containing the column to iterate through.
     * @param column The column that should be iterated through.
     *
     * @exception IllegalArgumentException Throws IllegalArgumentException if column
     *                                     is outside the valid range [0,9).
     */
    public ColumnIterator(Sudoku sudoku, int column) {
        if (column < 0 || column >= Sudoku.COL_SIZE) {
            throw new IllegalArgumentException(
                    String.format("column: %d is outside the range of the sudoku board [0,9).", column));
        }

        mCollection = sudoku;
        mColumn = column;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return mRow < Sudoku.ROW_SIZE;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.Iterator#next()
     */
    @Override
    public Integer next() {
        var val = peek();
        mRow++;
        return val;
    }

    /**
     * @see no.ntnu.imt3281.sudoku.SudokuIterator#getPosition()
     */
    @Override
    public RowColumPair getPosition() {
        return new RowColumPair(mRow, mColumn);
    }

    /**
     * @see no.ntnu.imt3281.sudoku.SudokuIterator#peek()
     */
    @Override
    public Integer peek() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        return mCollection.getElement(mRow, mColumn);
    }
}
