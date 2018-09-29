package no.ntnu.imt3281.sudoku;

/**
 * BadNumberException is a sudoku specific exception indicating that the
 * specified value one is trying to add to a sudoku board is invalid. i.e. it
 * already exists within the row/column or sub-grid
 *
 * @see no.ntnu.imt3281.sudoku.Sudoku#addNumber(int, int, int)
 */
@SuppressWarnings("serial")
public class BadNumberException extends IllegalArgumentException {
    final SudokuIterator mIterator;

    /**
     * Creates a BadNumberException holding the iterator argument.
     *
     * @param it The iterator to the first element found which contains the value
     *           that one is trying to duplicate.
     *
     * @see no.ntnu.imt3281.sudoku.Sudoku#addNumber(int, int, int)
     */
    public BadNumberException(SudokuIterator it) {
        mIterator = it;
    }

    public SudokuIterator getIterator() {
        return mIterator;
    }
}
