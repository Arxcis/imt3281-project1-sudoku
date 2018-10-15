package no.ntnu.imt3281.sudoku;


import static org.junit.Assert.*;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

import org.junit.Test;


public class SudokuControllerTest {

    @Test 
    public void testMakeBadGrid() {
        ArrayList<ArrayList<Integer>> badGrid = SudokuController.makeBadGrid();

        for (int row = 0; row < Sudoku.ROW_SIZE; row++) {
            for (int col = 0; col < Sudoku.COL_SIZE; col++) {
                assertEquals("", Sudoku.EMPTY_CELL, badGrid.get(row).get(col).intValue());
            }
        }        
    }

    @Test
    public void testAddValidNewval() {
        final int row = 0;
        final int col = 0;
        final int expected = 4;
        ArrayList<ArrayList<Integer>> badGrid = SudokuController.makeBadGrid();        
        Sudoku sudoku = new Sudoku();

        SudokuController.addNewvalToSudoku("4", row, col, sudoku, badGrid);

        assertEquals("", expected, sudoku.getElement(row, col));
    }

    @Test
    public void testAddNewvalToLockedCell() {
        final int row = 0;
        final int col = 0;
        final int expected = 5;
        ArrayList<ArrayList<Integer>> badGrid = SudokuController.makeBadGrid();        
        Sudoku sudoku = new Sudoku();
        String[] validNumbersList = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };

        sudoku.addNumber(row, col, expected);
        sudoku.lockNumbers();
    
        for (String validNumber : validNumbersList) {
            SudokuController.addNewvalToSudoku(validNumber, row, col, sudoku, badGrid);
            assertEquals("", expected, sudoku.getElement(row, col));
        }
    }

    @Test
    public void testAddEmptyNewval() {
        final int row = 0;
        final int col = 0;
        final int initialNumber = 5;
        ArrayList<ArrayList<Integer>> badGrid = SudokuController.makeBadGrid();        
        Sudoku sudoku = new Sudoku();        

        sudoku.addNumber(row, col, initialNumber);
        SudokuController.addNewvalToSudoku("", row, col, sudoku, badGrid);

        assertEquals("", Sudoku.EMPTY_CELL, sudoku.getElement(row, col));
    }

    @Test
    public void testAddNotANumber() {
        final int row = 0;
        final int col = 0;
        ArrayList<ArrayList<Integer>> badGrid = SudokuController.makeBadGrid();        
        Sudoku sudoku = new Sudoku();
        String[] notNumbersList = {
            "notANumber",
            "reallyNotAnumber",
            "134A1",
            "--___",
        };

        for (String notNumber : notNumbersList) {
            SudokuController.addNewvalToSudoku(notNumber, row, col, sudoku, badGrid);
            assertEquals("", Sudoku.EMPTY_CELL, sudoku.getElement(row, col));
        }
    }

    @Test
    public void testAddOutOfRangeNumbers() {
        final int row = 0;
        final int col = 0;
        ArrayList<ArrayList<Integer>> badGrid = SudokuController.makeBadGrid();        
        Sudoku sudoku = new Sudoku();
        String[] outOfRangeNumbersList = {
            "9999",
            "0",
            "-1",
            "10",
            "1337",
        };

        for (String outOfRangeNumber : outOfRangeNumbersList) {
            SudokuController.addNewvalToSudoku(outOfRangeNumber, row, col, sudoku, badGrid);
            assertEquals("", Sudoku.EMPTY_CELL, sudoku.getElement(row, col));
        }
    }

    @Test 
    public void testAddIllegalSudokuNumber() {
        ArrayList<ArrayList<Integer>> badGrid = SudokuController.makeBadGrid();        
        Sudoku sudoku = new Sudoku();
        
        SudokuController.addNewvalToSudoku("1", 0,0, sudoku, badGrid);        
        for (var i = 1; i < 9; ++i) {
            SudokuController.addNewvalToSudoku("1", i, 0, sudoku, badGrid);
        }

        assertEquals("", 1, sudoku.getElement(0, 0));
        assertEquals("", Sudoku.EMPTY_CELL, badGrid.get(0).get(0).intValue());
        for (var i = 1; i < 9; ++i) {
            assertEquals("", Sudoku.EMPTY_CELL, sudoku.getElement(i, 0));
            assertEquals("", 1, badGrid.get(i).get(0).intValue());
        }
    }

    @Test
    public void testRetryBadNumbers() {
        ArrayList<ArrayList<Integer>> badGrid = SudokuController.makeBadGrid();        
        Sudoku sudoku = new Sudoku();
        
        SudokuController.addNewvalToSudoku("1", 0,0, sudoku, badGrid);        
        for (var i = 1; i < 9; ++i) {
            SudokuController.addNewvalToSudoku("1", i, 0, sudoku, badGrid);
        }

        SudokuController.addNewvalToSudoku("", 0,0,sudoku,badGrid);
        SudokuController.retryBadNumbers(sudoku, badGrid);
        
        assertEquals("", 1, sudoku.getElement(1,0));
        assertEquals("", Sudoku.EMPTY_CELL, badGrid.get(1).get(0).intValue());        
    }
}