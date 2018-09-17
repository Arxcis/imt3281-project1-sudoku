package no.ntnu.imt3281.sudoku;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator used to iterate through a sub grid in a collection.
 */
public class SubGridIterator implements SudokuIterator {
    private List<List<Integer>> mCollection;
    private int mSubGrid;
    private int mCellIdx;

    /**
     * Creates a sub grid iterator that will iterate through the selected sub grid
     * in the supplied collection.
     *
     * @param collection The collection containing the sub grid to iterate through.
     * @param subGrid    The sub grid that should be iterated through.
     *
     * @exception Throws IllegalArgumentException if subGrid is outside the valid
     *                   range [0,9).
     */
    public SubGridIterator(List<List<Integer>> collection, int subGrid) {
        if (subGrid < 0 || subGrid >= 9)
            throw new IllegalArgumentException(
                    String.format("subGrid: %d is outside the range of the sudoku board [0,9).", subGrid));

        mCollection = collection;
        mSubGrid = subGrid;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return mCellIdx < 9;
    }

    /*
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

    /*
     * @see no.ntnu.imt3281.sudoku.SudokuIterator#getPosition()
     */
    @Override
    public Cell getPosition() {
        int row = (mSubGrid / 3) * 3 + mCellIdx / 3;
        int col = (mSubGrid % 3) * 3 + mCellIdx % 3;
        return new Cell(row, col);
    }

    /*
     * @see no.ntnu.imt3281.sudoku.SudokuIterator#peek()
     */
    @Override
    public Integer peek() {
        if (!hasNext())
            throw new NoSuchElementException();

        // Logic for calculating row and col taken from:
        // https://stackoverflow.com/questions/22289144/how-to-get-the-sudoku-2d-array-index-given-its-sub-grid-and-cell-in-the-sub
        int row = (mSubGrid / 3) * 3 + mCellIdx / 3;
        int col = (mSubGrid % 3) * 3 + mCellIdx % 3;

        return mCollection.get(row).get(col);
    }
}
