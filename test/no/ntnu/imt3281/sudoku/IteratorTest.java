package no.ntnu.imt3281.sudoku;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Test;

public class IteratorTest {
    ///////////////////////////////////////////////////////////////////////////
    /// Sunshine tests
    ///////////////////////////////////////////////////////////////////////////
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

        var board = Sudoku.parseSudokuFromJson(map);
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

        var board = Sudoku.parseSudokuFromJson(map);
        var it = board.iterator(ColumnIterator.class, 0);
        int counter = 1;
        while (it.hasNext()) {
            assertTrue(it.next() == counter++);
        }
        assertTrue(it.hasNext() == false && counter == 10);
    }

    @Test
    public void iterateThroughSubGrid() {
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

        var board = Sudoku.parseSudokuFromJson(map);
        var it = board.iterator(SubGridIterator.class, 0);
        int counter = 1;
        while (it.hasNext()) {
            assertTrue(it.next() == counter++);
        }
        assertTrue(it.hasNext() == false && counter == 10);
    }

    @Test
    public void iterateThroughMiddleSubGrid() {
        var map = "[\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1,  1,  2,  3, -1, -1, -1],\n" +
                "[-1, -1, -1,  4,  5,  6, -1, -1, -1],\n" +
                "[-1, -1, -1,  7,  8,  9, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1]\n" +
                "]";

        var board = Sudoku.parseSudokuFromJson(map);
        var it = board.iterator(SubGridIterator.class, 4);
        int counter = 1;
        while (it.hasNext()) {
            assertTrue(it.next() == counter++);
        }
        assertTrue(it.hasNext() == false && counter == 10);
    }

    ///////////////////////////////////////////////////////////////////////////
    /// Information tests
    ///////////////////////////////////////////////////////////////////////////

    @Test
    public void rowIteratorSupplyEnoughInformationToIdentifyLocation() {
        var map = "[\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1]\n" +
                "]";

        var board = Sudoku.parseSudokuFromJson(map);
        var it = board.iterator(RowIterator.class, 0);
        int counter = 0;
        while (it.hasNext()) {

            var location = it.getPosition();
            assertTrue(location.getRow() == 0 && location.getColumn() == counter++ && it.getClass() == RowIterator.class);
            it.next();
        }
    }

    @Test
    public void columnIteratorSupplyEnoughInformationToIdentifyLocation() {
        var map = "[\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1]\n" +
                "]";

        var board = Sudoku.parseSudokuFromJson(map);
        var it = board.iterator(ColumnIterator.class, 0);
        int counter = 0;
        while (it.hasNext()) {

            var location = it.getPosition();
            assertTrue(location.getRow() == counter++ && location.getColumn() == 0 && it.getClass() == ColumnIterator.class);
            it.next();
        }
    }

    @Test
    public void subGridIteratorSupplyEnoughInformationToIdentifyLocation() {
        var map = "[\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1],\n" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1]\n" +
                "]";

        int[] rowIndices = {0, 0, 0, 1, 1, 1, 2, 2, 2};
        int[] colIndices = {0, 1, 2, 0, 1, 2, 0, 1, 2};

        var board = Sudoku.parseSudokuFromJson(map);
        var it = board.iterator(SubGridIterator.class, 0);
        int counter = 0;
        while (it.hasNext()) {
            var location = it.getPosition();
            assertTrue(location.getRow() == rowIndices[counter] && location.getColumn() == colIndices[counter]
                    && it.getClass() == SubGridIterator.class);
            counter++;

            it.next();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    /// Exception tests
    ///////////////////////////////////////////////////////////////////////////
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

        var board = Sudoku.parseSudokuFromJson(map);
        board.iterator(Sudoku.class, 0);
    }

    @Test
    public void iteratorThrowOnInvalidRange() {
        Class<?>[] iterators = {RowIterator.class, ColumnIterator.class, SubGridIterator.class};
        int[] values = {-1, 9, 10};
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

        var board = Sudoku.parseSudokuFromJson(map);

        for (int i = 0; i < iterators.length; i++) {
            for (int j = 0; j < values.length; j++) {
                try {
                    board.iterator(iterators[i], values[j]);
                    fail(String.format("iterator i (%d, %s) should have thrown an IllegalArgumentException", i, iterators[i].toString()));
                } catch (IllegalArgumentException e) {

                }
            }
        }
    }

    @Test
    public void iteratorThrowOnIteratingOutOfBounds() {
        Class<?>[] iterators = {RowIterator.class, ColumnIterator.class, SubGridIterator.class};
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

        var board = Sudoku.parseSudokuFromJson(map);

        for (int i = 0; i < iterators.length; i++) {
            var it = board.iterator(iterators[i], 0);
            while (it.hasNext()) {
                it.next();
            }
            try {
                it.next();
                fail(String.format("iterator i (%d, %s) should have thrown a NoSuchElementException", i, iterators[i].toString()));
            } catch (NoSuchElementException e) {

            }
        }
    }

}
