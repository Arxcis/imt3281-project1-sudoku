package no.ntnu.imt3281.sudoku;

import static org.junit.Assert.assertTrue;

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

    /**
     * Tests if we can parse the sudoku supplied with the project.
     */
    @Test
    public void testJSONParseSuccessSuppliedSudoku() {
        var string = "[[5, 3, -1, -1, 7, -1, -1, -1, -1],\n" +
                "[6, -1, -1, 1, 9, 5, -1, -1, -1], \n" +
                "[-1, 9, 8, -1, -1, -1, -1, 6, -1], \n" +
                "[8, -1, -1, -1, 6, -1, -1, -1, 3], \n" +
                "[4, -1, -1, 8, -1, 3, -1, -1, 1], \n" +
                "[7, -1, -1, -1, 2, -1, -1, -1, 6], \n" +
                "[-1, 6, -1, -1, -1, -1, 2, 8, -1], \n" +
                "[-1, -1, -1, 4, 1, 9, -1, -1, 5], \n" +
                "[-1, -1, -1, -1, 8, -1, -1, 7, 9]]";

        int[][] desired = {{5, 3, -1, -1, 7, -1, -1, -1, -1},
                {6, -1, -1, 1, 9, 5, -1, -1, -1},
                {-1, 9, 8, -1, -1, -1, -1, 6, -1},
                {8, -1, -1, -1, 6, -1, -1, -1, 3},
                {4, -1, -1, 8, -1, 3, -1, -1, 1},
                {7, -1, -1, -1, 2, -1, -1, -1, 6},
                {-1, 6, -1, -1, -1, -1, 2, 8, -1},
                {-1, -1, -1, 4, 1, 9, -1, -1, 5},
                {-1, -1, -1, -1, 8, -1, -1, 7, 9}};

        var sudoku = Sudoku.loadSudokuFromJson(string);
        for (int row = 0; row < desired.length; row++) {
            for (int col = 0; col < desired[row].length; col++) {
                assertTrue(sudoku.getElement(row, col) == desired[row][col]);
            }
        }
    }

    /**
     * Tests if we can parse a sudoku found online, mainly to avoid having just one
     * test case. Found at: https://www.websudoku.com/
     */
    @Test
    public void testJSONParseSuccessOnlineSudoku() {
        var string = "[\n" +
                "[-1, -1, -1, 1, -1, -1, 5, 9, -1],\n" +
                "[-1, -1, 1, -1, 9, -1, -1, 8, 4],\n" +
                "[-1, -1, 8, -1, 3, -1, 7, -1, 6],\n" +
                "[8, 3, -1, 9, 5, -1, -1, -1, -1],\n" +
                "[-1, 5, 6, 3, -1, 1, 8, 7, -1],\n" +
                "[-1, -1, -1, -1, 4, 6, -1, 3, 5],\n" +
                "[1, -1, 3, -1, 7, -1, 4, -1, -1],\n" +
                "[6, 8, -1, -1, 1, -1, 9, -1, -1],\n" +
                "[-1, 4, 9, -1, -1, 5, -1, -1, -1]\n" +
                "]";

        int[][] desired = {
                {-1, -1, -1, 1, -1, -1, 5, 9, -1},
                {-1, -1, 1, -1, 9, -1, -1, 8, 4},
                {-1, -1, 8, -1, 3, -1, 7, -1, 6},
                {8, 3, -1, 9, 5, -1, -1, -1, -1},
                {-1, 5, 6, 3, -1, 1, 8, 7, -1},
                {-1, -1, -1, -1, 4, 6, -1, 3, 5},
                {1, -1, 3, -1, 7, -1, 4, -1, -1},
                {6, 8, -1, -1, 1, -1, 9, -1, -1},
                {-1, 4, 9, -1, -1, 5, -1, -1, -1}
                };

        var sudoku = Sudoku.loadSudokuFromJson(string);
        for (int row = 0; row < desired.length; row++) {
            for (int col = 0; col < desired[row].length; col++) {
                assertTrue(sudoku.getElement(row, col) == desired[row][col]);
            }
        }
    }

    @Test
    public void addNumberAllow0BasedRow() {
        var sudoku = new Sudoku();
        sudoku.addNumber(0, 1, 1);
        assertTrue(sudoku.getElement(0, 1) == 1);
    }

    @Test
    public void addNumberAllow0BasedCol() {
        var sudoku = new Sudoku();
        sudoku.addNumber(1, 0, 1);
        assertTrue(sudoku.getElement(1, 0) == 1);
    }

    @Test(expected = BadNumberException.class)
    public void addNumberThrowsOnIdenticalValueInSubGrid() {
        var sudoku = new Sudoku();
        sudoku.addNumber(0, 0, 1);
        sudoku.addNumber(0, 1, 1);
    }

    @Test
    public void iterateThroughRow() {
        var map = "[\n" +
                "[1, 2, 3, 4, 5, 6, 7, 8, 9],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1]\n" +
                "]";

        var board = Sudoku.loadSudokuFromJson(map);
        var it = board.iterator(RowIterator.class, 0);
        int counter = 1;
        while (it.hasNext()) {
            assertTrue(it.next() == counter++);
        }
        assertTrue(it.hasNext() == false && counter == 10);
    }

    @Test
    public void iterateThroughColumn() {
        var map = "[\n" +
                "[1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[2, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[3, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[4, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[5, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[6, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[7, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[8, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[9, -1, -1, -1, -1, -1, -1, -1, -1]\n" +
                "]";

        var board = Sudoku.loadSudokuFromJson(map);
        var it = board.iterator(ColumnIterator.class, 0);
        int counter = 1;
        while (it.hasNext()) {
            assertTrue(it.next() == counter++);
        }
        assertTrue(it.hasNext() == false && counter == 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void iteratorThrowsOnInvalidClass() {
        var map = "[\n" +
                "[1,  2,  3, -1, -1, -1, -1, -1, -1],\n" +
                "[4,  5,  6, -1, -1, -1, -1, -1, -1],\n" +
                "[7,  8,  9, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1]\n" +
                "]";

        var board = Sudoku.loadSudokuFromJson(map);
        var it = board.iterator(Sudoku.class, 0);
    }

}
