package no.ntnu.imt3281.sudoku;

@SuppressWarnings("serial")
public class BadNumberException extends IllegalArgumentException {
    final SudokuIterator mIterator;

    public BadNumberException(SudokuIterator it) {
        mIterator = it;
    }

    public SudokuIterator getIterator() {
        return mIterator;
    }
}
