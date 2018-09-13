package no.ntnu.imt3281.sudoku;

import java.util.Iterator;
import java.util.List;

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
     * @param collection The collection containing the row to iterate through
     * @param column     The column that should be iterated through.
     */
    public ColumnIterator(List<List<Integer>> collection, int column) {
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
        if (this.hasNext())
            return mCollection.get(mRow++).get(mColumn);

        return null;
    }

}
