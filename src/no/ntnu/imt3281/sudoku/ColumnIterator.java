package no.ntnu.imt3281.sudoku;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator used to iterate through a column in a collection.
 */
public class ColumnIterator implements Iterator<Integer> {
    private List<List<Integer>> mCollection;
    private int mRow;
    private int mColumn;

    /**
     * Creates a column iterator that will iterate through the selected column in
     * the supplied collection.
     *
     * @param collection The collection containing the column to iterate through.
     * @param column     The column that should be iterated through.
     *
     * @exception Throws IllegalArgumentException if column is outside the valid
     *                   range [0,9).
     */
    public ColumnIterator(List<List<Integer>> collection, int column) {
        if (column < 0 || column >= 9)
            throw new IllegalArgumentException(
                    String.format("column: %d is outside the range of the sudoku board [0,9).", column));

        mCollection = collection;
        mColumn = column;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return mRow < mCollection.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Iterator#next()
     */
    @Override
    public Integer next() {
        if (!this.hasNext())
            throw new NoSuchElementException();

        return mCollection.get(mRow++).get(mColumn);
    }
}
