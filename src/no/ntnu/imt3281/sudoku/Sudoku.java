package no.ntnu.imt3281.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    private List<List<Integer>> mSudokuBoard;

    /**
     * Creates a new sudoku board from the given board
     * 
     * @param board The new board
     */
    public Sudoku(final List<List<Integer>> board) {
        mSudokuBoard = new ArrayList<>();
        for (var row : board) {
            mSudokuBoard.add(new ArrayList<>(row));
        }
    }
}
