package no.ntnu.imt3281.sudoku;

/**
 * Iterator created for iterating through a sudoku board. Also allows for
 * querying the current value "pointed to" and its row and column position.
 */
public interface SudokuIterator extends java.util.Iterator<Integer> {

    /**
     * Simple class for containing coordinates of a cell.
     */
    public class RowColumPair {
        private int mRow;
        private int mColumn;

        RowColumPair(int row, int column) {
            mRow = row;
            mColumn = column;
        }

        public int getRow() {
            return mRow;
        }

        public int getColumn() {
            return mColumn;
        }
    }

    /**
     * Gets the row and column position of the element pointed to by the iterator.
     *
     * @return RowColumnPair containing row and column of element pointed to by iterator.
     */
    RowColumPair getPosition();

    /**
     * Gets the value of the current element that is pointed to. I.e. it gets the
     * value without incrementing the iterator.
     *
     * @return Current value pointed to by the iterator.
     */
    Integer peek();
}
