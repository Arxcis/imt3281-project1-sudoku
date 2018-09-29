package no.ntnu.imt3281.sudoku;

import java.util.NoSuchElementException;

/**
 * Iterator used to iterate through a sub grid in a sudoku board.
 */
public class SubGridIterator implements SudokuIterator {
    private final Sudoku mCollection;
    private int mSubGrid;
    private int mCellIdx;

    /**
     * Creates a sub grid iterator that will iterate through the selected sub grid
     * in the supplied sudoku board.
     *
     * @param sudoku  The sudoku board containing the sub grid to iterate through.
     * @param subGrid The sub grid that should be iterated through.
     *
     * @exception IllegalArgumentException Throws IllegalArgumentException if
     *                                     subGrid is outside the valid range [0,9).
     */
    public SubGridIterator(Sudoku sudoku, int subGrid) {
        if (subGrid < 0 || subGrid >= Sudoku.GRID_COUNT) {
            throw new IllegalArgumentException(
                    String.format("subGrid: %d is outside the range of the sudoku board [0,9).", subGrid));
        }

        mCollection = sudoku;
        mSubGrid = subGrid;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return mCellIdx < Sudoku.GRID_COUNT;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.Iterator#next()
     */
    @Override
    public Integer next() {
        var value = peek();
        mCellIdx++;

        return value;
    }

    /**
     * @see no.ntnu.imt3281.sudoku.SudokuIterator#getPosition()
     */
    @Override
    public RowColumPair getPosition() {
        int row = (mSubGrid / 3) * 3 + mCellIdx / 3;
        int col = (mSubGrid % 3) * 3 + mCellIdx % 3;
        return new RowColumPair(row, col);
    }

    /**
     * @see no.ntnu.imt3281.sudoku.SudokuIterator#peek()
     */
    @Override
    public Integer peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        // Logic for calculating row and col taken from:
        // https://stackoverflow.com/questions/22289144/how-to-get-the-sudoku-2d-array-index-given-its-sub-grid-and-cell-in-the-sub
        int row = (mSubGrid / 3) * 3 + mCellIdx / 3;
        int col = (mSubGrid % 3) * 3 + mCellIdx % 3;

        return mCollection.getElement(row, col);
    }
}
