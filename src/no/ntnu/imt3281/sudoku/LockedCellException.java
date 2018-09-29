package no.ntnu.imt3281.sudoku;

/**
 * LockedCellException is thrown when one tries to edit a locked sudoku cell.
 *
 * @see no.ntnu.imt3281.sudoku.Sudoku#setElement(int, int, int)
 */
@SuppressWarnings("serial")
public class LockedCellException extends IllegalArgumentException {
}