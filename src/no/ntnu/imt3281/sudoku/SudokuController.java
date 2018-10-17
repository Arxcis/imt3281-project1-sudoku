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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.imt3281.language.LanguageBundler;

/**
 * SudokuController is responsible for the UI management of the sudoku game.
 */
public class SudokuController {

    @FXML
    Button mBtnExit;

    @FXML
    Button mBtnNewGame;

    @FXML
    Button mBtnSave;

    @FXML
    Button mBtnLoad;

    @FXML
    Button mBtnLock;

    @FXML
    Button mBtnUnlock;

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
     * Load and build a scene from an FXML document
     *
     * @exception IOException Throws IOException upon IO errors.
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
        mStage.close();
    }

    /**
     * Button handler. Creates a random new game of easy difficulty, and loads 
     * it into SudokuController and re-renders the scene.
     *
     * @param event fxml event object not used
     */
    @FXML
    void onClickNewEasyGame(ActionEvent event) {

        try {
            mSudoku = Sudoku.createSudokuOfDifficulty(Sudoku.Difficulty.EASY);
        } catch (IOException e) {
            reportErrorToUser(LanguageBundler.getBundle().getString("error.generic.ioexception"), "");
            return;
        }
        mBadGrid = SudokuController.makeBadGrid();
        this.render();
    }

    /**
     * Button-handler, creates a random new game of hard difficulty, 
     * loads it into SudokuController and re-renders the scene.
     *
     * @param event fxml event object not used
     */
    @FXML
    void onClickNewHardGame(ActionEvent event) {
        try {
            mSudoku = Sudoku.createSudokuOfDifficulty(Sudoku.Difficulty.HARD);
        } catch (IOException e) {
            reportErrorToUser(LanguageBundler.getBundle().getString("error.generic.ioexception"), "");
            return;
        }
        mBadGrid = SudokuController.makeBadGrid();
        this.render();
    }

    /**
     * Present load file dialog to user. If user cancel do nothing, else try to load file. If no success give error message to user. Re-renders UI.
     *
     * @see https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm 01.09.18
     */
    @FXML
    void onClickLoad(ActionEvent event) {
        mFileChooser.setTitle(LanguageBundler.getBundle().getString("load"));

        File file = mFileChooser.showOpenDialog(mStage);
        if (file == null) {
            return;
            // ... user did not select file
        }

        try {
            mSudoku = Sudoku.loadSudokuFromFile(file.toPath());
        } catch (InvalidSudokuBoardException e) {
            reportErrorToUser(LanguageBundler.getBundle().getString("error.invalid.file"), file.toPath().toString());
            return;
        } catch (IOException e) {
            reportErrorToUser(LanguageBundler.getBundle().getString("error.generic.ioexception"),
                    file.toPath().toString());
            return;
        }

        mSudoku.lockNumbers();
        mBadGrid = SudokuController.makeBadGrid();

        this.render();
    }

    /**
     * Present save file dialog to the user. If user cancel do nothing, else try to save file. Give error message if the saving failed. Re-renders UI.
     *
     * @see https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm 01.09.18
     */
    @FXML
    void onClickSave(ActionEvent event) {
        mFileChooser.setTitle(LanguageBundler.getBundle().getString("save"));

        File file = mFileChooser.showSaveDialog(mStage);
        if (file == null) {
            return;
            // ... user did not select file
        }
        try {
            Sudoku.saveSudokuToFile(mSudoku, file.toPath());

        } catch (IOException e) {
            reportErrorToUser(LanguageBundler.getBundle().getString("error.generic.ioexception"),
                    file.toPath().toString());
            return;
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
        assert mBtnExit != null : "fx:id=\"mBtnExit\" was not injected: check your FXML file 'View.fxml'.";
        assert mBtnNewGame != null : "fx:id=\"mBtnNewGame\" was not injected: check your FXML file 'View.fxml'.";
        assert mBtnSave != null : "fx:id=\"mBtnSave\" was not injected: check your FXML file 'View.fxml'.";
        assert mBtnLoad != null : "fx:id=\"mBtnLoad\" was not injected: check your FXML file 'View.fxml'.";
        assert mBtnLock != null : "fx:id=\"mBtnLock\" was not injected: check your FXML file 'View.fxml'.";
        assert mBtnUnlock != null : "fx:id=\"mBtnUnlock\" was not injected: check your FXML file 'View.fxml'.";
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
     * Event handler for value changed in a single fxml textfield. Abort this handler early in case rendering is going on, to prevent infinite loop.
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
     * Render scene with updated game state + bad numbers state.
     * If game is completed, display victory to the user.
     * The renderer changes the state of fxml, which triggers 
     * onChange callback functions, which may trigger more render calls.
     * This will cause an infinite callback-render loop. This is why we
     * lock guard this function using the mIsRendering flag.
     */
    void render() {
        mIsRendering.set(true);

        // Render order does matter
        SudokuController.resetRenderState(mTextGrid);
        SudokuController.renderValidNumbers(mSudoku, mTextGrid);
        SudokuController.renderBadNumbers(mBadGrid, mTextGrid);
        if (mSudoku.isSolved()) {
            SudokuController.renderSolved(mTextGrid);

            // Give output to user that they have solved the sudoku
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setTitle(LanguageBundler.getBundle().getString("congratulations") + "!");
            alert.setHeaderText(LanguageBundler.getBundle().getString("congratulations") + "!");
            alert.setContentText(LanguageBundler.getBundle().getString("solved.it") + "!");
            alert.showAndWait();

        }
        mIsRendering.set(false);
    }

    /**
     * Creates a popup error that is showed to the user with the supplied header
     * text and message.
     *
     * @param header  The header of the message.
     * @param message The content of the message.
     */
    static void reportErrorToUser(String header, String message) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(LanguageBundler.getBundle().getString("error") + "!");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Try add number to sudoku game state. If rejected, maybe keep the number
     * as a bad number, otherwise throw number away and continue game.
     * 
     * @param newval latest user input from a cell
     * @param row cell row index
     * @param col cell column index
     * @param outSudoku sudoku game state
     * @param outBadGrid 9x9 bad numbers game state.
     */
    static void addNewvalToSudoku(String newval, int row, int col, Sudoku outSudoku,
            ArrayList<ArrayList<Integer>> outBadGrid) {
        if (outSudoku.isNumberLocked(row, col)) {
            return;
            // ... do nothing
        }

        if ("".equals(newval)) {
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
     * @return 9x9 array of fxml textField
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
     * @return 9x9 array holding sudoku bad numbers state
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
     * Binding an 9x9 array of fxml textfields to a parent fxml grid pane.
     * The parent serves as a root element for all the text fields in the fxml scene.
     *
     * @param textGrid 9x9 array of fxml textfields
     * @param outParent fxml grid pane, parent of all textfields.
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
     * Retry adding bad numbers to the sudoku game state.
     * 
     * @param outSudoku sudoku game state
     * @param outBadGrid bad numbers state
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
     * Set all CSS classes to their initial state.
     *
     * @param outTextGrid 9x9 array of fxml textfields
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
     * Map valid numbers to UI
     *
     * @param sudoku Sudoku game state
     * @param outTextGrid 9x9 fxml textfields 
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
     * Map bad numbers to UI. Numbers not accepted by the soduku game state.
     * 
     * @param badGrid 9x9 array bad numbers state
     * @param outTextGrid 9x9 fxml textfields 
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
     * The game is completed. Render all numbers as solved.
     *  
     * @param outTextGrid 9x9 array fxml textfields 
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
