package no.ntnu.imt3281.sudoku;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class ViewController {
    /** 
     * Load and build a scene from an FXML document 
     * @return fxml scene
     */
    public static Scene loadScene() throws IOException {
        final URL fxml = ViewController.class.getResource("View.fxml");
        final Parent root = FXMLLoader.load(fxml, Language.getBundle());
        final Scene scene = new Scene(root);

        return scene;
    }

    /** 
     * Should be the only Sudoku instance in the application 
     */
    Sudoku mSudoku;
    
    @FXML /** fx:id="mBtnNewGame" */
    Button mBtnNewGame;

    @FXML /** fx:id="mBtnSave" */
    Button mBtnSave;

    @FXML /** fx:id="mBtnLoad" */
    Button mBtnLoad;

    @FXML /** fx:id="mBtnExit" */
    Button mBtnExit;

    @FXML /** fx:id="mGrid" */
    GridPane mGrid;

    @FXML
    void OnClickExit(ActionEvent event) {
        System.out.println("OnClickExit");
    }

    @FXML
    void OnClickLoad(ActionEvent event) {
        System.out.println("OnClickLoad");
    }

    @FXML
    void OnClickNewGame(ActionEvent event) {
        System.out.println("OnClickNewGame");
    }

    @FXML
    void OnClickSave(ActionEvent event) {
        System.out.println("OnClickSave");
    }

    @FXML
    void initialize() {

        // 1. Assert that all references are bound
        assert mBtnNewGame != null : "fx:id=\"btnNewGame\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert mBtnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert mBtnLoad != null : "fx:id=\"btnLoad\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert mBtnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert mGrid != null : "fx:id=\"grid\" was not injected: check your FXML file 'View.fxml'.";

        mSudoku = new Sudoku();

        for (int col = 0; col < Sudoku.COL_SIZE; ++col) {
            for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {

                int sudokuNumber = mSudoku.getElement(row, col);
                TextField cell = new TextField("");

                if (sudokuNumber > -1) {
                    cell.setText(Integer.toString(sudokuNumber));
                }

                AnchorPane.setTopAnchor(cell, 0.0);
                AnchorPane.setLeftAnchor(cell, 0.0);
                AnchorPane.setRightAnchor(cell, 0.0);
                AnchorPane.setBottomAnchor(cell, 0.0);
                cell.setAlignment(Pos.CENTER);
                cell.setFont(Font.font("Verdana", 20));

                AnchorPane anchor = new AnchorPane();
                anchor.setMinSize(50, 50);
                anchor.setPrefSize(50, 50);
                anchor.setMaxSize(200, 200);
                anchor.getChildren().add(cell);

                mGrid.add(anchor, col, row);

                final int finalrow = row;
                final int finalcol = col;
                cell.textProperty().addListener(
                    (__, ___, newval) -> 
                        this.ValueChangedInCell(cell, newval, finalrow, finalcol));
            }
        }
    }

    /**
     * Parse, Validate input. Clear cell if not valid 
     * @param cell fxml target 
     * @param newval latest user input
     * @param row sudoku row index
     * @param col sudoku column index
     */
    void ValueChangedInCell(TextField cell, String newval, int row,  int col) {
        LOG_DEBUG("Newval: " + newval);
        
        // 1. If newval is empty we set the
        if (newval.equals("")) {
            mSudoku.setElement(row, col, Sudoku.EMPTY_CELL);
            LOG_DEBUG("Newval == \"\" -> setting cell to Sudoku.EMPTY_CELL");
            ViewController.clearCell(cell);
            return;
        }
         
        // 2. Parse string -> int
        int candidate = 0;
        try {
            candidate = Integer.parseInt(newval);
        } catch (NumberFormatException e) {
            LOG_DEBUG("Integer.parseInt threw NumberFormatException: " + e.toString());
            ViewController.clearCell(cell);
            return;
        }
        
        // 3. Add number to Sudoku 
        try {
            mSudoku.addNumber(row, col, candidate);
        } catch (BadNumberException e) {
            LOG_DEBUG("mSudoku.addNumber threw BadNumberException: " + e.toString());
            ViewController.clearCell(cell);
            // TODO Color cell red
            return;
        } catch (IllegalArgumentException e) {
            LOG_DEBUG("mSudoku.addNumber threw IllegalArgumentException: " + e.toString());
            ViewController.clearCell(cell);
            return;
        }

        // 4. Number added
        LOG_DEBUG("Number added: " + Integer.toString(candidate)); 
    }
    
    /**
     * @param cell fxml target
     */
    static void clearCell(TextField cell) {
        Platform.runLater(() -> { 
            cell.clear(); 
        });
    }
    
    /**
     * TODO Move this to a more globalized place
     * @param message
     */
    static void LOG_DEBUG(String message) {
        boolean CONSOLE_DEBUG = false;
        if (CONSOLE_DEBUG) System.out.println(message);
    }
}
