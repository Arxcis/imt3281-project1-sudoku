package no.ntnu.imt3281.sudoku;

import java.util.Iterator;
import java.util.List;

/**
 * Iterator used to iterate through a row in a collection.
 */
public class RowIterator implements Iterator<Integer> {
    private List<List<Integer>> mCollection;
    private int mRow;
    private int mColumn;

    /**
     * Creates a row iterator that will iterate through the selected row in the
     * supplied collection.
     *
     * @param collection The collection containing the row to iterate through
     * @param row        The row that should be iterated through.
     */
    public RowIterator(List<List<Integer>> collection, int row) {
        mCollection = collection;
        mRow = row;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return mColumn < mCollection.get(mRow).size();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Iterator#next()
     */
    @Override
    public Integer next() {
        if (this.hasNext())
            return mCollection.get(mRow).get(mColumn++);

        return null;
    }

}
