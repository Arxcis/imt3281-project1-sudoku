package no.ntnu.imt3281.sudoku;

import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuTest {

	@Test
	public void testEmptyConstructor() {
		Sudoku sudoku = new Sudoku();
		assertTrue(sudoku instanceof Sudoku);
	}
}
