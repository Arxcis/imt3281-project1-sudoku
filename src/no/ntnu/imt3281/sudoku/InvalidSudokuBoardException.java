package no.ntnu.imt3281.sudoku;

/**
 * InvalidSudokuBoardException indicates that a Sudoku file is invalid, either
 * because it is not properly formatted, or because it is missing data.
 *
 * @see no.ntnu.imt3281.sudoku.Sudoku#loadSudokuFromFile(java.nio.file.Path)
 */
@SuppressWarnings("serial")
public class InvalidSudokuBoardException extends RuntimeException {
}
