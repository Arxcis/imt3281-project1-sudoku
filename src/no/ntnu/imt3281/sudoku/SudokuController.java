package no.ntnu.imt3281.sudoku;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.imt3281.language.LanguageBundler;

public class SudokuController {
    /**
     * Load and build a scene from an FXML document
     *
     * @return fxml scene
     */
    public static Scene loadScene() throws IOException {
        final URL fxml = SudokuController.class.getResource("View.fxml");
        final Parent root = FXMLLoader.load(fxml, LanguageBundler.getBundle());

        mScene = new Scene(root);
        mScene.getStylesheets().add(SudokuController.class.getResource("View.css").toString());

        return mScene;
    }

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
    ArrayList<ArrayList<Integer>> mBadGrid;

    /**
     * file chooser
     */
    FileChooser mFileChooser;

    /**
     * is rendering
     */
    AtomicBoolean mIsRendering = new AtomicBoolean(false);

    /**
     * fxml scene
     */
    static Scene mScene;

    /**
     * fxml stage
     */
    static Stage mStage;

    /**
     * right lines in 3x3 boxes
     */
    static PseudoClass mCSSRight = PseudoClass.getPseudoClass("right");

    /**
     * bottom lines 3x3 boxes
     */
    static PseudoClass mCSSBottom = PseudoClass.getPseudoClass("bottom");

    /**
     * bad numbers
     */
    static PseudoClass mCSSBad = PseudoClass.getPseudoClass("bad");

    /**
     * locked numbers
     */
    static PseudoClass mCSSLocked = PseudoClass.getPseudoClass("locked");

    /**
     * solved
     */
    static PseudoClass mCSSSolved = PseudoClass.getPseudoClass("solved");

    /**
     * setStage
     */
    static void setStage(Stage stage) {
        mStage = stage;
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
    void onClickNewGame(ActionEvent event) {

        mScene.getStylesheets().clear();
        mScene.getStylesheets().add(this.getClass().getResource("View.css").toString());

        System.out.println("OnClickNewGame");
    }

    /**
     * fxml event
     *
     * @see https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm 01.09.18
     */
    @FXML
    void onClickLoad(ActionEvent event) {
        mFileChooser.setTitle("Load Game");

        File file = mFileChooser.showOpenDialog(mStage);
        if (file == null) {
            return;
            // ... user did not select file
        }

        try {
            mSudoku = Sudoku.loadSudokuFromFile(file.toPath());
            mSudoku.lockNumbers();
            mBadGrid = SudokuController.makeBadGrid();

            this.render();

        } catch (InvalidSudokuFileException e) {
            // ... TODO handle file not valid
        } catch (IOException e) {
            // ... TODO handle file not success
        }
    }

    /**
     * fxml event
     *
     * @see https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm 01.09.18
     */
    @FXML
    void onClickSave(ActionEvent event) {
        mFileChooser.setTitle("Save Game");

        File file = mFileChooser.showSaveDialog(mStage);
        if (file == null) {
            return;
            // ... user did not select file
        }
        try {
            Sudoku.saveSudokuToFile(mSudoku, file.toPath());

        } catch (IOException e) {
            // ... TODO handle file not success
        }
    }

    /**
     * fxml event
     */
    @FXML
    void onClickLock(ActionEvent event) {
        mSudoku.lockNumbers();
        this.render();
    }

    /**
     * fxml event
     */
    @FXML
    void onClickUnlock(ActionEvent event) {
        mSudoku.unlockNumbers();
        this.render();
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
        mFileChooser = new FileChooser();
        mBadGrid = SudokuController.makeBadGrid();
        mTextGrid = SudokuController.makeTextGrid();
        this.addTextGridOnChangedListeners();
        SudokuController.bindTextGridToParent(mTextGrid, mGridParent);
        this.render();
    }

    /**
     * Add event listeners to each fxml text field
     */
    void addTextGridOnChangedListeners() {
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {

                TextField textCell = mTextGrid.get(row).get(col);
                final int finalrow = row;
                final int finalcol = col;
                textCell.textProperty()
                        .addListener((__, ___, newval) -> this.valueChangedInCell(newval, finalrow, finalcol));
            }
        }
    }

    /**
     * Parse, Validate input. Clear cell if not valid
     *
     * @param newval latest user input
     * @param row    sudoku row index
     * @param col    sudoku column index
     */
    void valueChangedInCell(String newval, int row, int col) {

        if (mIsRendering.get()) {
            return;
            // ...dont handle anything while rendering to prevent infinite loop
        }

        SudokuController.addNewvalToSudoku(newval, row, col, mSudoku, mBadGrid);
        SudokuController.retryBadNumbers(mSudoku, mBadGrid);
        this.render();
    }

    /**
     * render
     */
    void render() {
        mIsRendering.set(true);

        // Render order does matter
        SudokuController.resetRenderState(mTextGrid);
        SudokuController.renderValidNumbers(mSudoku, mTextGrid);
        SudokuController.renderBadNumbers(mBadGrid, mTextGrid);
        if (mSudoku.isSolved()) {
            SudokuController.renderSolved(mTextGrid);
        }
        mIsRendering.set(false);
    }

    /**
     * @param newval latest user input
     * @param row    sudoku row index
     * @param col    sudoku column index
     * @param outSudoku
     * @param outBadGrid
     */
    static void addNewvalToSudoku(String newval, int row, int col, Sudoku outSudoku, ArrayList<ArrayList<Integer>> outBadGrid) {
        if (outSudoku.isNumberLocked(row, col)) {
            return;
            // ... do nothing
        }

        if (newval.equals("")) {
            outSudoku.setElement(row, col, Sudoku.EMPTY_CELL);
            outBadGrid.get(row).set(col, Sudoku.EMPTY_CELL);
            return;
            // ...cell was cleared by user
        }

        int candidate = 0;
        try {
            candidate = Integer.parseInt(newval);

        } catch (NumberFormatException e) {
            return;
            // ...do nothing. The cell will be cleared
        }

        try {
            outSudoku.addNumber(row, col, candidate);
            // ...success

        } catch (BadNumberException e) {
            outBadGrid.get(row).set(col, candidate);
            // ...keeping the cell in the bad numbers grid.

        } catch (IllegalArgumentException e) {
            // ...do nothing. The cell will be cleared
        }
    }

    /**
     * @return 2d array of fxml textField
     */
    static ArrayList<ArrayList<TextField>> makeTextGrid() {
        var textGrid = new ArrayList<ArrayList<TextField>>();

        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            var textRow = new ArrayList<TextField>();

            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {
                TextField textCell = new TextField();
                textCell.getStyleClass().add("cell");
                textRow.add(textCell);
            }
            textGrid.add(textRow);
        }
        return textGrid;
    }

    /**
     * @return empty grid of bad numbers
     */
    static ArrayList<ArrayList<Integer>> makeBadGrid() {
        var badGrid = new ArrayList<ArrayList<Integer>>();

        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            var badRow = new ArrayList<Integer>();

            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {
                badRow.add(Integer.valueOf(Sudoku.EMPTY_CELL));
            }
            badGrid.add(badRow);
        }
        return badGrid;
    }

    /**
     * @param textGrid
     * @param outParent
     */
    static void bindTextGridToParent(ArrayList<ArrayList<TextField>> textGrid, GridPane outParent) {

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
                anchor.pseudoClassStateChanged(SudokuController.mCSSRight, col == 2 || col == 5);
                anchor.pseudoClassStateChanged(SudokuController.mCSSBottom, row == 2 || row == 5);

                outParent.add(anchor, col, row);
            }
        }
    }

    /**
     * @param outSudoku
     * @param outBadGrid
     */
    static void retryBadNumbers(Sudoku outSudoku, ArrayList<ArrayList<Integer>> outBadGrid) {

        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {

                int badNumber = outBadGrid.get(row).get(col);
                if (badNumber == Sudoku.EMPTY_CELL) {
                    continue;
                }
                try {
                    outSudoku.addNumber(row, col, badNumber);
                    outBadGrid.get(row).set(col, Sudoku.EMPTY_CELL);
                } catch (Exception e) {
                    // ...keep bad numbers by doing nothing
                }
            }
        }
    }

    /**
     * @param  outTextGrid
     */
    static void resetRenderState(ArrayList<ArrayList<TextField>> outTextGrid) {
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {
                TextField textCell = outTextGrid.get(row).get(col);
                textCell.pseudoClassStateChanged(SudokuController.mCSSSolved, false);
                textCell.pseudoClassStateChanged(SudokuController.mCSSBad, false);
                textCell.pseudoClassStateChanged(SudokuController.mCSSLocked, false);
            }
        }
    }

    /**
     * @param sudoku
     * @param outTextGrid
     */
    static void renderValidNumbers(Sudoku sudoku, ArrayList<ArrayList<TextField>> outTextGrid) {
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {

                int sudokuNumber = sudoku.getElement(row, col);
                TextField textCell = outTextGrid.get(row).get(col);

                if (sudokuNumber == Sudoku.EMPTY_CELL) {
                    textCell.clear();
                    continue;
                }

                if (sudoku.isNumberLocked(row, col)) {
                    textCell.pseudoClassStateChanged(SudokuController.mCSSLocked, true);
                }
                textCell.setText(Integer.toString(sudokuNumber));
            }
        }
    }

    /**
     * @param badGrid
     * @param outTextGrid
     */
    static void renderBadNumbers(ArrayList<ArrayList<Integer>> badGrid, ArrayList<ArrayList<TextField>> outTextGrid) {
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {

                int badNumber = badGrid.get(row).get(col);
                if (badNumber == Sudoku.EMPTY_CELL) {
                    continue;
                }

                TextField textCell = outTextGrid.get(row).get(col);
                textCell.pseudoClassStateChanged(SudokuController.mCSSBad, true);
                textCell.setText(Integer.toString(badNumber));
            }
        }
    }

    /**
     * @param outTextGrid
     */
    static void renderSolved(ArrayList<ArrayList<TextField>> outTextGrid) {
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
            for (int col = 0; col < Sudoku.COL_SIZE; ++col) {
                TextField textCell = outTextGrid.get(row).get(col);
                textCell.pseudoClassStateChanged(SudokuController.mCSSSolved, true);
            }
        }
    }
}
