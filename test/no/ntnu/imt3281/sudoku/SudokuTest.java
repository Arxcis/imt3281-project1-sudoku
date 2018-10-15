package no.ntnu.imt3281.sudoku;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class SudokuTest {
    ///////////////////////////////////////////////////////
    /// Constructor tests
    ///////////////////////////////////////////////////////
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
                "[-1, -1, -1,  1, -1, -1,  5,  9, -1],\n" +
                "[-1, -1,  1, -1,  9, -1, -1,  8,  4],\n" +
                "[-1, -1,  8, -1,  3, -1,  7, -1,  6],\n" +
                "[ 8,  3, -1,  9,  5, -1, -1, -1, -1],\n" +
                "[-1,  5,  6,  3, -1,  1,  8,  7, -1],\n" +
                "[-1, -1, -1, -1,  4,  6, -1,  3,  5],\n" +
                "[ 1, -1,  3, -1,  7, -1,  4, -1, -1],\n" +
                "[ 6,  8, -1, -1,  1, -1,  9, -1, -1],\n" +
                "[-1,  4,  9, -1, -1,  5, -1, -1, -1]\n" +
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

    ///////////////////////////////////////////////////////
    /// AddNumber tests
    ///////////////////////////////////////////////////////
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

    @Test
    public void addNumberThrowsOnIdenticalValueInRow() {
        var sudoku = new Sudoku();
        sudoku.addNumber(0, 1, 1);
        try {
            sudoku.addNumber(0, 0, 1);
            fail("sudoku should have thrown a BadNumberException");
        } catch (BadNumberException e) {
            var it = e.getIterator();
            assertTrue(it.peek() == 1);
            assertTrue(it.getClass() == RowIterator.class);

            var position = it.getPosition();
            assertTrue(position.getRow() == 0 && position.getColumn() == 1);
        }
    }

    @Test
    public void addNumberThrowsOnIdenticalValueInColumn() {
        var sudoku = new Sudoku();
        sudoku.addNumber(1, 0, 1);
        try {
            sudoku.addNumber(0, 0, 1);
            fail("sudoku should have thrown a BadNumberException");
        } catch (BadNumberException e) {
            var it = e.getIterator();
            assertTrue(it.peek() == 1);
            assertTrue(it.getClass() == ColumnIterator.class);

            var position = it.getPosition();
            assertTrue(position.getRow() == 1 && position.getColumn() == 0);
        }
    }

    @Test
    public void addNumberThrowsOnIdenticalValueInSubGrid() {
        var sudoku = new Sudoku();
        sudoku.addNumber(0, 2, 1);

        try {
            sudoku.addNumber(2, 0, 1);
            fail("sudoku should have thrown a BadNumberException");
        } catch (BadNumberException e) {
            var it = e.getIterator();
            assertTrue(it.peek() == 1);
            assertTrue(it.getClass() == SubGridIterator.class);

            var position = it.getPosition();
            assertTrue(position.getRow() == 0 && position.getColumn() == 2);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNumberThrowsIllegalArgumentExceptionOnInvalidLowValue() {
        var sudoku = new Sudoku();
        sudoku.addNumber(0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNumberThrowsIllegalArgumentExceptionOnInvalidHighValue() {
        var sudoku = new Sudoku();
        sudoku.addNumber(0, 0, 10);
    }

    ///////////////////////////////////////////////////////
    /// Lock Number tests
    ///////////////////////////////////////////////////////
    @Test(expected = LockedCellException.class)
    public void lockedNumbersThrowOnChange() {
        var string = "[[5, 3, -1, -1, 7, -1, -1, -1, -1],\n" +
                "[6, -1, -1, 1, 9, 5, -1, -1, -1], \n" +
                "[-1, 9, 8, -1, -1, -1, -1, 6, -1], \n" +
                "[8, -1, -1, -1, 6, -1, -1, -1, 3], \n" +
                "[4, -1, -1, 8, -1, 3, -1, -1, 1], \n" +
                "[7, -1, -1, -1, 2, -1, -1, -1, 6], \n" +
                "[-1, 6, -1, -1, -1, -1, 2, 8, -1], \n" +
                "[-1, -1, -1, 4, 1, 9, -1, -1, 5], \n" +
                "[-1, -1, -1, -1, 8, -1, -1, 7, 9]]";

        var sudoku = Sudoku.loadSudokuFromJson(string);
        sudoku.lockNumbers();
        sudoku.addNumber(0, 0, 1);
    }

    @Test
    public void lockedNumbersAreCorrectlyIdentified() {
        var string = "[[5, 3, -1, -1, 7, -1, -1, -1, -1],\n" +
                "[6, -1, -1, 1, 9, 5, -1, -1, -1], \n" +
                "[-1, 9, 8, -1, -1, -1, -1, 6, -1], \n" +
                "[8, -1, -1, -1, 6, -1, -1, -1, 3], \n" +
                "[4, -1, -1, 8, -1, 3, -1, -1, 1], \n" +
                "[7, -1, -1, -1, 2, -1, -1, -1, 6], \n" +
                "[-1, 6, -1, -1, -1, -1, 2, 8, -1], \n" +
                "[-1, -1, -1, 4, 1, 9, -1, -1, 5], \n" +
                "[-1, -1, -1, -1, 8, -1, -1, 7, 9]]";

        var sudoku = Sudoku.loadSudokuFromJson(string);
        sudoku.lockNumbers();
        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                if (sudoku.getElement(row, col) != Sudoku.EMPTY_CELL) {
                    assertTrue(sudoku.isNumberLocked(row, col));
                }
            }
        }
    }

    @Test
    public void emptyCellsAreNotLocked() {
        var sudoku = new Sudoku();
        sudoku.lockNumbers();
        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                assertTrue(sudoku.isNumberLocked(row, col) == false);
            }
        }
    }

    @Test
    public void canStillChangeUnLockedCells() {
        var string = "[[5, 3, -1, -1, 7, -1, -1, -1, -1],\n" +
                "[6, -1, -1, 1, 9, 5, -1, -1, -1], \n" +
                "[-1, 9, 8, -1, -1, -1, -1, 6, -1], \n" +
                "[8, -1, -1, -1, 6, -1, -1, -1, 3], \n" +
                "[4, -1, -1, 8, -1, 3, -1, -1, 1], \n" +
                "[7, -1, -1, -1, 2, -1, -1, -1, 6], \n" +
                "[-1, 6, -1, -1, -1, -1, 2, 8, -1], \n" +
                "[-1, -1, -1, 4, 1, 9, -1, -1, 5], \n" +
                "[-1, -1, -1, -1, 8, -1, -1, 7, 9]]";

        var sudoku = Sudoku.loadSudokuFromJson(string);
        sudoku.lockNumbers();
        sudoku.addNumber(0, 2, 4);
    }

    ///////////////////////////////////////////////////////
    /// Saving and loading tests
    ///////////////////////////////////////////////////////
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void savesFileSuccessfully() throws Exception {
        var string =
                "[[5, 3, -1, -1, 7, -1, -1, -1, -1],\n" +
                "[6, -1, -1, 1, 9, 5, -1, -1, -1], \n" +
                "[-1, 9, 8, -1, -1, -1, -1, 6, -1], \n" +
                "[8, -1, -1, -1, 6, -1, -1, -1, 3], \n" +
                "[4, -1, -1, 8, -1, 3, -1, -1, 1], \n" +
                "[7, -1, -1, -1, 2, -1, -1, -1, 6], \n" +
                "[-1, 6, -1, -1, -1, -1, 2, 8, -1], \n" +
                "[-1, -1, -1, 4, 1, 9, -1, -1, 5], \n" +
                "[-1, -1, -1, -1, 8, -1, -1, 7, 9]]";

        var sudoku = Sudoku.loadSudokuFromJson(string);
        sudoku.lockNumbers();
        sudoku.addNumber(0, 2, 4);

        var file = folder.newFile().toPath();
        Sudoku.saveSudokuToFile(sudoku, file);

        String[] desiredText = { "5 1, 3 1, 4 0, -1 0, 7 1, -1 0, -1 0, -1 0, -1 0,",
                "6 1, -1 0, -1 0, 1 1, 9 1, 5 1, -1 0, -1 0, -1 0,",
                "-1 0, 9 1, 8 1, -1 0, -1 0, -1 0, -1 0, 6 1, -1 0,",
                "8 1, -1 0, -1 0, -1 0, 6 1, -1 0, -1 0, -1 0, 3 1,",
                "4 1, -1 0, -1 0, 8 1, -1 0, 3 1, -1 0, -1 0, 1 1,",
                "7 1, -1 0, -1 0, -1 0, 2 1, -1 0, -1 0, -1 0, 6 1,",
                "-1 0, 6 1, -1 0, -1 0, -1 0, -1 0, 2 1, 8 1, -1 0,",
                "-1 0, -1 0, -1 0, 4 1, 1 1, 9 1, -1 0, -1 0, 5 1,",
                "-1 0, -1 0, -1 0, -1 0, 8 1, -1 0, -1 0, 7 1, 9 1," };

        String line;
        try (var reader = new BufferedReader(new FileReader(file.toString()))) {
            int count = 0;
            while ((line = reader.readLine()) != null) {
                var trimmed = line.trim();
                assertTrue(trimmed.compareTo(desiredText[count++]) == 0);
            }
        }
    }

    @Test
    public void loadsFileSuccessfully() throws IOException {
        var fileContents = "5 1, 3 1, 4 0, -1 0, 7 1, -1 0, -1 0, -1 0, -1 0, \n"
                + "6 1, -1 0, -1 0, 1 1, 9 1, 5 1, -1 0, -1 0, -1 0, \n"
                + "-1 0, 9 1, 8 1, -1 0, -1 0, -1 0, -1 0, 6 1, -1 0, \n"
                + "8 1, -1 0, -1 0, -1 0, 6 1, -1 0, -1 0, -1 0, 3 1, \n"
                + "4 1, -1 0, -1 0, 8 1, -1 0, 3 1, -1 0, -1 0, 1 1, \n"
                + "7 1, -1 0, -1 0, -1 0, 2 1, -1 0, -1 0, -1 0, 6 1, \n"
                + "-1 0, 6 1, -1 0, -1 0, -1 0, -1 0, 2 1, 8 1, -1 0, \n"
                + "-1 0, -1 0, -1 0, 4 1, 1 1, 9 1, -1 0, -1 0, 5 1, \n"
                + "-1 0, -1 0, -1 0, -1 0, 8 1, -1 0, -1 0, 7 1, 9 1, ";

        var file = folder.newFile();
        try (final var writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContents);
        }

        var sudoku = Sudoku.loadSudokuFromFile(file.toPath());

        int[] desired = {5, 1, 3, 1, 4, 0, -1, 0, 7, 1, -1, 0, -1, 0, -1, 0, -1, 0,
                6, 1, -1, 0, -1, 0, 1, 1, 9, 1, 5, 1, -1, 0, -1, 0, -1, 0,
                -1, 0, 9, 1, 8, 1, -1, 0, -1, 0, -1, 0, -1, 0, 6, 1, -1, 0,
                8, 1, -1, 0, -1, 0, -1, 0, 6, 1, -1, 0, -1, 0, -1, 0, 3, 1,
                4, 1, -1, 0, -1, 0, 8, 1, -1, 0, 3, 1, -1, 0, -1, 0, 1, 1,
                7, 1, -1, 0, -1, 0, -1, 0, 2, 1, -1, 0, -1, 0, -1, 0, 6, 1,
                -1, 0, 6, 1, -1, 0, -1, 0, -1, 0, -1, 0, 2, 1, 8, 1, -1, 0,
                -1, 0, -1, 0, -1, 0, 4, 1, 1, 1, 9, 1, -1, 0, -1, 0, 5, 1,
                -1, 0, -1, 0, -1, 0, -1, 0, 8, 1, -1, 0, -1, 0, 7, 1, 9, 1};

        for (int row = 0, i = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                assertTrue(sudoku.getElement(row, col) == desired[i++]);
                assertTrue(sudoku.isNumberLocked(row, col) == (desired[i++] == 1 ? true : false));
            }
        }
    }

    @Test(expected = InvalidSudokuFileException.class)
    public void loadsFileThrowsOnIncompleteNumberOfRows() throws IOException {
        var fileContents = "5 1, 3 1, 4 0, -1 0, 7 1, -1 0, -1 0, -1 0, -1 0, \n"
                + "6 1, -1 0, -1 0, 1 1, 9 1, 5 1, -1 0, -1 0, -1 0, \n"
                + "-1 0, 9 1, 8 1, -1 0, -1 0, -1 0, -1 0, 6 1, -1 0, \n"
                + "8 1, -1 0, -1 0, -1 0, 6 1, -1 0, -1 0, -1 0, 3 1, \n"
                + "4 1, -1 0, -1 0, 8 1, -1 0, 3 1, -1 0, -1 0, 1 1, \n"
                + "7 1, -1 0, -1 0, -1 0, 2 1, -1 0, -1 0, -1 0, 6 1, \n"
                + "-1 0, 6 1, -1 0, -1 0, -1 0, -1 0, 2 1, 8 1, -1 0, \n"
                + "-1 0, -1 0, -1 0, 4 1, 1 1, 9 1, -1 0, -1 0, 5 1, \n";

        var file = folder.newFile();
        try (final var writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContents);
        }

        Sudoku.loadSudokuFromFile(file.toPath());
    }

    @Test(expected = InvalidSudokuFileException.class)
    public void loadsFileThrowsOnIncompleteNumberOfColumns() throws IOException {
        var fileContents = "5 1, 3 1, 4 0, -1 0, 7 1, -1 0, -1 0, -1 0, \n"
                + "6 1, -1 0, -1 0, 1 1, 9 1, 5 1, -1 0, -1 0, \n"
                + "-1 0, 9 1, 8 1, -1 0, -1 0, -1 0, -1 0, 6 1, \n"
                + "8 1, -1 0, -1 0, -1 0, 6 1, -1 0, -1 0, -1 0, \n"
                + "4 1, -1 0, -1 0, 8 1, -1 0, 3 1, -1 0, -1 0, \n"
                + "7 1, -1 0, -1 0, -1 0, 2 1, -1 0, -1 0, -1 0, \n"
                + "-1 0, 6 1, -1 0, -1 0, -1 0, -1 0, 2 1, 8 1, \n"
                + "-1 0, -1 0, -1 0, 4 1, 1 1, 9 1, -1 0, -1 0, \n"
                + "-1 0, -1 0, -1 0, -1 0, 8 1, -1 0, -1 0, 7 1, ";

        var file = folder.newFile();
        try (final var writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContents);
        }

        Sudoku.loadSudokuFromFile(file.toPath());
    }

    @Test(expected = InvalidSudokuFileException.class)
    public void loadsFileThrowsOnTooManyColumns() throws IOException {
        var fileContents = "5 1, 3 1, 4 0, -1 0, 7 1, -1 0, -1 0, -1 0, -1 0, -1 0, \n"
                + "6 1, -1 0, -1 0, 1 1, 9 1, 5 1, -1 0, -1 0, -1 0, -1 0, \n"
                + "-1 0, 9 1, 8 1, -1 0, -1 0, -1 0, -1 0, 6 1, -1 0, -1 0, \n"
                + "8 1, -1 0, -1 0, -1 0, 6 1, -1 0, -1 0, -1 0, 3 1, -1 0, \n"
                + "4 1, -1 0, -1 0, 8 1, -1 0, 3 1, -1 0, -1 0, 1 1, -1 0, \n"
                + "7 1, -1 0, -1 0, -1 0, 2 1, -1 0, -1 0, -1 0, 6 1, -1 0, \n"
                + "-1 0, 6 1, -1 0, -1 0, -1 0, -1 0, 2 1, 8 1, -1 0, -1 0, \n"
                + "-1 0, -1 0, -1 0, 4 1, 1 1, 9 1, -1 0, -1 0, 5 1, -1 0, \n"
                + "-1 0, -1 0, -1 0, -1 0, 8 1, -1 0, -1 0, 7 1, 9 1, -1 0, ";

        var file = folder.newFile();
        try (final var writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContents);
        }

        Sudoku.loadSudokuFromFile(file.toPath());
    }

    @Test(expected = InvalidSudokuFileException.class)
    public void loadsFileThrowsOnTooManyRows() throws IOException {
        var fileContents = "5 1, 3 1, 4 0, -1 0, 7 1, -1 0, -1 0, -1 0, -1 0, \n"
                + "6 1, -1 0, -1 0, 1 1, 9 1, 5 1, -1 0, -1 0, -1 0, \n"
                + "-1 0, 9 1, 8 1, -1 0, -1 0, -1 0, -1 0, 6 1, -1 0, \n"
                + "8 1, -1 0, -1 0, -1 0, 6 1, -1 0, -1 0, -1 0, 3 1, \n"
                + "4 1, -1 0, -1 0, 8 1, -1 0, 3 1, -1 0, -1 0, 1 1, \n"
                + "7 1, -1 0, -1 0, -1 0, 2 1, -1 0, -1 0, -1 0, 6 1, \n"
                + "-1 0, 6 1, -1 0, -1 0, -1 0, -1 0, 2 1, 8 1, -1 0, \n"
                + "-1 0, -1 0, -1 0, 4 1, 1 1, 9 1, -1 0, -1 0, 5 1, \n"
                + "-1 0, -1 0, -1 0, -1 0, 8 1, -1 0, -1 0, 7 1, 9 1, \n"
                + "-1 0, -1 0, -1 0, -1 0, -1 0, -1 0, -1 0, -1 0, -1 0,";

        var file = folder.newFile();
        try (final var writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContents);
        }

        Sudoku.loadSudokuFromFile(file.toPath());
    }

    @Test(expected = InvalidSudokuFileException.class)
    public void loadsFileThrowsOnLockedEmptyCells() throws IOException {
        var fileContents = "5 1, 3 1, 4 0, -1 1, 7 1, -1 0, -1 0, -1 0, -1 0, \n"
                + "6 1, -1 0, -1 0, 1 1, 9 1, 5 1, -1 0, -1 0, -1 0, \n"
                + "-1 0, 9 1, 8 1, -1 0, -1 0, -1 0, -1 0, 6 1, -1 0, \n"
                + "8 1, -1 0, -1 0, -1 0, 6 1, -1 0, -1 0, -1 0, 3 1, \n"
                + "4 1, -1 0, -1 0, 8 1, -1 0, 3 1, -1 0, -1 0, 1 1, \n"
                + "7 1, -1 0, -1 0, -1 0, 2 1, -1 0, -1 0, -1 0, 6 1, \n"
                + "-1 0, 6 1, -1 0, -1 0, -1 0, -1 0, 2 1, 8 1, -1 0, \n"
                + "-1 0, -1 0, -1 0, 4 1, 1 1, 9 1, -1 0, -1 0, 5 1, \n"
                + "-1 0, -1 0, -1 0, -1 0, 8 1, -1 0, -1 0, 7 1, 9 1, ";

        var file = folder.newFile();
        try (final var writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContents);
        }

        Sudoku.loadSudokuFromFile(file.toPath());
    }

    ///////////////////////////////////////////////////////
    /// Change all elements tests
    ///////////////////////////////////////////////////////
    /**
     * Logic of the test is that if all if we change all the numbers of the same
     * value to a different value between the original and the randomized board,
     * then we should be able to identify that all the values that have changed by
     * finding the difference between the original and new values, and checking if
     * that difference is always the same each time we encounter a number within the
     * original table.
     */
    @Test
    public void randomizesAllContainingAvalue() {
        var string = "["+
                     "[ 5,  3, -1, -1,  7, -1, -1, -1, -1], \n" +
                     "[ 6, -1, -1,  1,  9,  5, -1, -1, -1], \n" +
                     "[-1,  9,  8, -1, -1, -1, -1,  6, -1], \n" +
                     "[ 8, -1, -1, -1,  6, -1, -1, -1,  3], \n" +
                     "[ 4, -1, -1,  8, -1,  3, -1, -1,  1], \n" +
                     "[ 7, -1, -1, -1,  2, -1, -1, -1,  6], \n" +
                     "[-1,  6, -1, -1, -1, -1,  2,  8, -1], \n" +
                     "[-1, -1, -1,  4,  1,  9, -1, -1,  5], \n" +
                     "[-1, -1, -1, -1,  8, -1, -1,  7,  9]" +
                     "]";

        var originalBoard = Sudoku.loadSudokuFromJson(string);
        var newBoard = Sudoku.loadSudokuFromJson(string);
        newBoard.randomizeAllExistingElements();

        // Using -2 to indicate value not being set, as we can end up with 0 as legal
        // value in the check.
        final int UNSET_VALUE = -2;
        var results = new ArrayList<>(java.util.Collections.nCopies(9, UNSET_VALUE));

        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                var origValue = originalBoard.getElement(row, col);
                var newVal = newBoard.getElement(row, col);

                // If we encounter an EMPTY_CELL we don't need to do any calculations we only
                // need to check that we have an EMPTY_CELL on the other board.
                if (origValue == Sudoku.EMPTY_CELL) {
                    assertTrue(newVal == Sudoku.EMPTY_CELL);
                    continue;
                }

                // We are encountering this value in the original table for the first time.
                if (results.get(origValue - 1) == UNSET_VALUE) {
                    results.set(origValue - 1, origValue - newVal);
                }

                assertTrue(results.get(origValue - 1) == origValue - newVal);
            }
        }
    }

    /////////////////////////////////////////////////////
    /// Flip board tests
    /////////////////////////////////////////////////////
    @Test
    public void flipBoardHorizontalTest() {
        var string = "[[5, 3, -1, -1, 7, -1, -1, -1, -1],\n" + "[6, -1, -1, 1, 9, 5, -1, -1, -1], \n"
                + "[-1, 9, 8, -1, -1, -1, -1, 6, -1], \n" + "[8, -1, -1, -1, 6, -1, -1, -1, 3], \n"
                + "[4, -1, -1, 8, -1, 3, -1, -1, 1], \n" + "[7, -1, -1, -1, 2, -1, -1, -1, 6], \n"
                + "[-1, 6, -1, -1, -1, -1, 2, 8, -1], \n" + "[-1, -1, -1, 4, 1, 9, -1, -1, 5], \n"
                + "[-1, -1, -1, -1, 8, -1, -1, 7, 9]]";

        var originalBoard = Sudoku.loadSudokuFromJson(string);
        var sudoku = Sudoku.loadSudokuFromJson(string);
        sudoku.flipBoard(Sudoku.Axis.HORIZONTAL);

        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                assertTrue(sudoku.getElement(row, col) == originalBoard.getElement(Sudoku.ROW_SIZE - row - 1, col));
            }
        }
    }

    @Test
    public void flipBoardVerticalTest() {
        var string = "[[5, 3, -1, -1, 7, -1, -1, -1, -1],\n" + "[6, -1, -1, 1, 9, 5, -1, -1, -1], \n"
                + "[-1, 9, 8, -1, -1, -1, -1, 6, -1], \n" + "[8, -1, -1, -1, 6, -1, -1, -1, 3], \n"
                + "[4, -1, -1, 8, -1, 3, -1, -1, 1], \n" + "[7, -1, -1, -1, 2, -1, -1, -1, 6], \n"
                + "[-1, 6, -1, -1, -1, -1, 2, 8, -1], \n" + "[-1, -1, -1, 4, 1, 9, -1, -1, 5], \n"
                + "[-1, -1, -1, -1, 8, -1, -1, 7, 9]]";

        var originalBoard = Sudoku.loadSudokuFromJson(string);
        var sudoku = Sudoku.loadSudokuFromJson(string);
        sudoku.flipBoard(Sudoku.Axis.VERTICAL);

        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                assertTrue(sudoku.getElement(row, col) == originalBoard.getElement(row, Sudoku.COL_SIZE - col - 1));
            }
        }
    }

    @Test
    public void flipBoardDiagonalSlashTest() {
        var string = "[[5, 3, -1, -1, 7, -1, -1, -1, -1],\n" + "[6, -1, -1, 1, 9, 5, -1, -1, -1], \n"
                + "[-1, 9, 8, -1, -1, -1, -1, 6, -1], \n" + "[8, -1, -1, -1, 6, -1, -1, -1, 3], \n"
                + "[4, -1, -1, 8, -1, 3, -1, -1, 1], \n" + "[7, -1, -1, -1, 2, -1, -1, -1, 6], \n"
                + "[-1, 6, -1, -1, -1, -1, 2, 8, -1], \n" + "[-1, -1, -1, 4, 1, 9, -1, -1, 5], \n"
                + "[-1, -1, -1, -1, 8, -1, -1, 7, 9]]";

        var originalBoard = Sudoku.loadSudokuFromJson(string);
        var sudoku = Sudoku.loadSudokuFromJson(string);
        sudoku.flipBoard(Sudoku.Axis.DIAGONALSLASH);

        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                assertTrue(sudoku.getElement(row, col) == originalBoard.getElement(Sudoku.ROW_SIZE - col - 1,
                        Sudoku.COL_SIZE - row - 1));
            }
        }
    }

    @Test
    public void flipBoardDiagonalBackslashTest() {
        var string = "[[5, 3, -1, -1, 7, -1, -1, -1, -1],\n" + "[6, -1, -1, 1, 9, 5, -1, -1, -1], \n"
                + "[-1, 9, 8, -1, -1, -1, -1, 6, -1], \n" + "[8, -1, -1, -1, 6, -1, -1, -1, 3], \n"
                + "[4, -1, -1, 8, -1, 3, -1, -1, 1], \n" + "[7, -1, -1, -1, 2, -1, -1, -1, 6], \n"
                + "[-1, 6, -1, -1, -1, -1, 2, 8, -1], \n" + "[-1, -1, -1, 4, 1, 9, -1, -1, 5], \n"
                + "[-1, -1, -1, -1, 8, -1, -1, 7, 9]]";

        var originalBoard = Sudoku.loadSudokuFromJson(string);
        var sudoku = Sudoku.loadSudokuFromJson(string);
        sudoku.flipBoard(Sudoku.Axis.DIAGONALBACKSLASH);

        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                assertTrue(sudoku.getElement(row, col) == originalBoard.getElement(col, row));
            }
        }
    }
}
