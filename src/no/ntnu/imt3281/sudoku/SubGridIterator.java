package no.ntnu.imt3281.sudoku;

import java.util.Iterator;
import java.util.List;

/**
 * Iterator used to iterate through a sub grid in a collection.
 */
public class SubGridIterator implements Iterator<Integer> {
    private List<List<Integer>> mCollection;
    private int mSubGrid;
    private int mCellIdx;

    /**
     * Creates a sub grid iterator that will iterate through the selected sub grid
     * in the supplied collection.
     *
     * @param collection The collection containing the row to iterate through
     * @param subGrid    The sub grid that should be iterated through.
     */
    public SubGridIterator(List<List<Integer>> collection, int subGrid) {
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
        if (!hasNext())
            return null;

        // Logic for calculating row and col taken from:
        // https://stackoverflow.com/questions/22289144/how-to-get-the-sudoku-2d-array-index-given-its-sub-grid-and-cell-in-the-sub
        int row = (mSubGrid / 3) * 3 + mCellIdx / 3;
        int col = (mSubGrid % 3) * 3 + mCellIdx % 3;
        mCellIdx++;

        return mCollection.get(row).get(col);
    }

}
