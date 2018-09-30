package no.ntnu.imt3281.sudoku;

import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SudokuController {
    
    /**
     * fxml id
     */
    @FXML
    Button mBtnNewGame;

    /**
     * fxml id
     */
    @FXML
    Button mBtnSave;

    /**
     * fxml id
     */
    @FXML
    Button mBtnLoad;

    /**
     * fxml id
     */
    @FXML
    Button mBtnExit;

    /**
     * fxml id
     */
    @FXML
    GridPane mGridParent;

    /**
     * sudoku game state object
     */
    Sudoku mSudoku;
    
    /**
     * grid of fxml textFields
     */
    ArrayList<ArrayList<TextField>> mTextGrid;
    
    /**
     * grid of bad numbers
     */
    ArrayList<ArrayList<int>> mBadGrid;

    /**
     * fxml scene
     */    
    static Scene mScene;

    /**
      * Pseudo class inspiration @see https://stackoverflow.com/a/34225599 22.09.18
      */
    static PseudoClass cssRight = PseudoClass.getPseudoClass("right");
    
    /**
      * Pseudo class inspiration @see https://stackoverflow.com/a/34225599 22.09.18
      */
    static PseudoClass cssBottom = PseudoClass.getPseudoClass("bottom");
    
    /**
      * Pseudo class inspiration @see https://stackoverflow.com/a/34225599 22.09.18
      */
    static PseudoClass cssBad = PseudoClass.getPseudoClass("bad");

    /**
     * Load and build a scene from an FXML document
     *
     * @return fxml scene
     */
    public static Scene loadScene() throws IOException {
        final URL fxml = SudokuController.class.getResource("View.fxml");
        final Parent root = FXMLLoader.load(fxml, Language.getBundle());

        mScene = new Scene(root);
        mScene.getStylesheets().add(SudokuController.class.getResource("View.css").toString());

        return mScene;
    }


    /**
     * fxml event
     */
    @FXML
    void onClickExit(ActionEvent event) {
        System.out.println("OnClickExit");
    }

    /**
     * fxml event
     */
    @FXML
    void onClickLoad(ActionEvent event) {
        System.out.println("OnClickLoad");
    }

    /**
     * fxml event
     */
    @FXML
    void onClickNewGame(ActionEvent event) {

        mScene.getStylesheets().clear();
        mScene.getStylesheets().add(this.getClass().getResource("View.css").toString());

        System.out.println("OnClickNewGame");
    }

    /**
     * fxml event
     */
    @FXML
    void onClickSave(ActionEvent event) {
        System.out.println("OnClickSave");
    }

    /**
     * fxml event
     */
    @FXML
    void initialize() {

        assert mBtnNewGame != null : "fx:id=\"btnNewGame\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert mBtnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert mBtnLoad != null : "fx:id=\"btnLoad\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert mBtnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert mGridParent != null : "fx:id=\"mGridParent\" was not injected: check your FXML file 'View.fxml'.";

        mSudoku = new Sudoku();
        mBadGrid = SudokuController.makeBadGrid();
        mTextGrid = SudokuController.makeTextGrid();
        SudokuController.bindTextGridToParent(mGridParent, mTextGrid);

        // Add eventlisteners to each textCell ()
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {

                TextField textCell = mTextGrid.get(row).get(col);
                final int finalrow = row;
                final int finalcol = col;
                textCell.textProperty().addListener(
                    (__, ___, newval) -> 
                        this.valueChangedInCell(newval, finalrow, finalcol));
            }
        }
    }

   /**
     * Parse, Validate input. Clear cell if not valid
     *
     * @param cell   fxml target
     * @param newval latest user input
     * @param row    sudoku row index
     * @param col    sudoku column index
     */
    void valueChangedInCell(String newval, int row,  int col) {

        // 1. If newval is empty, we clear cell..
        if (!newval.equals("")) {
            // 2. Parse string -> int
            int candidate = 0;
            try {
                candidate = Integer.parseInt(newval);
                // 3. Add number to Sudoku
                try {
                    mSudoku.addNumber(row, col, candidate);
                    // ...new cell added to the sudoku board
                
                } catch (BadNumberException e) {
                    mBadGrid.get(row).get(col) = candidate;
                    // ...kept the cell in the bad numbers grid.
                } catch (IllegalArgumentException e) {
                    // ...do nothing. The cell will be cleared        
                }
            } catch (NumberFormatException e) {
                // ...do nothing. The cell will be cleared
            }
        } else {
            mSudoku.setElement(row, col, Sudoku.EMPTY_CELL);
            // ...cell was cleared by user
        }

        SudokuController.retryBadNumbers(mSudoku, mBadGrid);

        // Run later because fxml complains if we modifiy textField in its onChanged handler
        Platform.runLater(() -> {
            SudokuController.renderValidNumbers(mTextGrid, mSudoku);
            SudokuController.renderBadNumbers(mTextGrid, mBadGrid);
        });
    }

    /**
     * @return 2d array of fxml textField
     */
    static ArrayList<ArrayList<TextField>> makeTextGrid() {
        var textGrid = new ArrayList<>();
        
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            textGrid.add(new ArrayList<>());
        
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {
                TextField textCell = new TextField();
                textCell.getStyleClass().add("cell");
                textGrid.get(row).add(textCell);
            }
        }
        return textGrid;
    }

    /**
      * @return empty grid of bad numbers
      */
    static ArrayList<ArrayList<int>> makeBadGrid() {

        var badGrid = new ArrayList<>();
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            badGrid.add(new ArrayList<>());
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {
                badGrid.add(Sudoku.EMPTY_CELL);
            }
        }
        return badGrid;
    }

    /**
     * @param parent
     * @param textGrid
     */
    static void bindTextGridToParent(GridPane parent, ArrayList<ArrayList<TextField>> textGrid) {

        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {

                TextField textCell = textGrid.get(row).get(col);
                AnchorPane anchor = new AnchorPane();

                AnchorPane.setTopAnchor(textCell, 0.0);
                AnchorPane.setLeftAnchor(textCell, 0.0);
                AnchorPane.setRightAnchor(textCell, 0.0);
                AnchorPane.setBottomAnchor(textCell, 0.0);
                anchor.getChildren().add(textCell);
                
                anchor.setMinSize(50, 50);
                anchor.setPrefSize(50, 50);
                anchor.setMaxSize(200, 200);

                anchor.getStyleClass().add("anchor");
                anchor.pseudoClassStateChanged(SudokuController.cssRight, col == 2 || col == 5);
                anchor.pseudoClassStateChanged(SudokuController.cssBottom, row == 2 || row == 5);

                parent.add(anchor, col, row);
            }
        }
    }

    /**
     * @param sudoku
     * @param badGrid
     */
    static void retryBadNumbers(Sudoku sudoku, ArrayList<ArrayList<int>> badGrid) {
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) { 

                int badNumber = badGrid.get(row).get(col);                
                if (badNumber == Sudoku.EMPTY_CELL) {
                    continue;
                }

                try {
                    sudoku.addNumber(row, col, badNumber);
                    badGrid.get(row).get(col) = Sudoku.EMPTY_CELL;
                } catch (Exception e) {
                    // ...keep bad number around by doing nothing
                }
            }
        }   
    }

    /**
     * @param textGrid
     * @param sudoku
     */
    static void renderValidNumbers(ArrayList<ArrayList<TextField>> textGrid, Sudoku sudoku) {

        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {

                int sudokuNumber = mSudoku.getElement(row, col);
                TextField textCell = mTextGrid.get(row).get(col);
                if (sudokuNumber == Sudoku.EMPTY_CELL) {
                    textCell.clear();
                } else {
                    textCell.pseudoClassStateChanged(SudokuController.cssBad, false);
                    textCell.setText(Integer.toString(sudokuNumber));
                }
            }
        }
    }

    /**
     * @param textGrid
     * @param badGrid
     */
    static void renderBadNumbers(ArrayList<ArrayList<TextField>> textGrid, ArrayList<ArrayList<int>> badGrid) {
        
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {

                int badNumber = badGrid.get(row).get(col);
                if (badNumber == Sudoku.EMPTY_CELL) {
                    continue;
                }

                TextField textCell = mTextGrid.get(row).get(col);
                textCell.pseudoClassStateChanged(SudokuController.cssBad, true);
                textCell.setText(Integer.toString(badNumber));
            }
        }
    }
}
