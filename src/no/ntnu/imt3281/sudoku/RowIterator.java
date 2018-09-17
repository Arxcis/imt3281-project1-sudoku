package no.ntnu.imt3281.sudoku;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator used to iterate through a row in a collection.
 */
public class RowIterator implements SudokuIterator {
    private List<List<Integer>> mCollection;
    private int mRow;
    private int mColumn;

    /**
     * Creates a row iterator that will iterate through the selected row in the
     * supplied collection.
     *
     * @param collection The collection containing the row to iterate through.
     * @param row        The row that should be iterated through.
     *
     * @exception Throws IllegalArgumentException if row is outside the valid range
     *                   [0,9).
     */
    public RowIterator(List<List<Integer>> collection, int row) {
        if (row < 0 || row >= Sudoku.ROW_SIZE)
            throw new IllegalArgumentException(
                    String.format("row: %d is outside the range of the sudoku board [0,9).", row));

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
        return mColumn < Sudoku.COL_SIZE;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Iterator#next()
     */
    @Override
    public Integer next() {
        var val = peek();
        mColumn++;
        return val;
    }

    /*
     * @see no.ntnu.imt3281.sudoku.SudokuIterator#getPosition()
     */
    @Override
    public Cell getPosition() {
        return new Cell(mRow, mColumn);
    }

    /*
     * @see no.ntnu.imt3281.sudoku.SudokuIterator#peek()
     */
    @Override
    public Integer peek() {
        if (!this.hasNext())
            throw new NoSuchElementException();

        return mCollection.get(mRow).get(mColumn);
    }
}
