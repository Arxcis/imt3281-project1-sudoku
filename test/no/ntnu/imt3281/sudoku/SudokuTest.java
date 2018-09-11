package no.ntnu.imt3281.sudoku;

import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuTest {

    @Test
    public void testEmptyConstructor() {
        Sudoku sudoku = new Sudoku();
        assertTrue(sudoku instanceof Sudoku);
        
        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                assertTrue(sudoku.getElement(row, col) == Sudoku.EMPTY_CELL);
            }
        }
    }
}
