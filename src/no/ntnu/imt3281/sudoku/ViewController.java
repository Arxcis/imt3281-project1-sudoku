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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class ViewController {

    /** Load and build a scene from an FXML document */
    public static Scene loadScene() throws IOException {
        final URL fxml = ViewController.class.getResource("View.fxml");
        final Parent root = FXMLLoader.load(fxml);
        final Scene scene = new Scene(root);

        return scene;
    }

    /** Should be the ONLY sudoku instance in the application */
    private Sudoku mSudoku;

    @FXML // fx:id="mBtnNewGame"
    private Button mBtnNewGame;

    @FXML // fx:id="mBtnSave"
    private Button mBtnSave;

    @FXML // fx:id="mBtnLoad"
    private Button mBtnLoad;

    @FXML // fx:id="mBtnExit"
    private Button mBtnExit;

    @FXML // fx:id="mGrid"
    private GridPane mGrid;

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
    void OnKeyInCell(KeyEvent event) {
        System.out.println("On: " + event.getTarget().toString());
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

        for (Integer col = 0; col < Sudoku.COL_SIZE; ++col) {
            for (Integer row = 0; row < Sudoku.ROW_SIZE; ++row) {

                Integer sudokuNumber = mSudoku.getElement(row, col);
                TextField textfield = new TextField("");

                if (sudokuNumber > -1) {
                    textfield.setText(sudokuNumber.toString());
                }

                AnchorPane.setTopAnchor(textfield, 0.0);
                AnchorPane.setLeftAnchor(textfield, 0.0);
                AnchorPane.setRightAnchor(textfield, 0.0);
                AnchorPane.setBottomAnchor(textfield, 0.0);
                textfield.setAlignment(Pos.CENTER);
                textfield.setFont(Font.font("Verdana", 20));

                AnchorPane anchor = new AnchorPane();
                anchor.setMinSize(50, 50);
                anchor.setPrefSize(50, 50);
                anchor.setMaxSize(200, 200);
                anchor.getChildren().add(textfield);

                mGrid.add(anchor, col, row);

                final Integer localcol = col;
                final Integer localrow = row;
                textfield.textProperty().addListener(
                        (target, oldvalue, newvalue) -> this.ValueChangedInCell(textfield, oldvalue, newvalue, localcol, localrow));
            }
        }
    }

    /** Format, Parse, Validate input. Clear cell if not valid */
    void ValueChangedInCell(TextField textfield, String oldval, String newval, Integer col, Integer row) {

        String text = textfield.getText();
        System.out.print("\nTest: "  + text + " Oldval: " + oldval + "  NEwval: " + newval + "   -->  ");
        
        // TODO - Whenever a user removes a valid number from the GUI, it also need to be removed from the Sudoku class - JSolsvik 18.09.18
        
        if (oldval == newval) {
            this.errorValueChangedInCell(textfield, "oldval == newval");
            return;
        }
        
        if (text.length() != 1) {
            this.errorValueChangedInCell(textfield, "text.length() != 1");
            return;
        }
        
        final char candidateChar = text.charAt(0);
        if (!Character.isDigit(candidateChar)) {
            this.errorValueChangedInCell(textfield, "!Character.isDigit(candidateChar)");
            return;
        }

        final Integer candidate = Character.getNumericValue(candidateChar);
        if (candidate == 0) {
            this.errorValueChangedInCell(textfield, "candidate == 0");
            return;
        }
        
        try {
            mSudoku.addNumber(row, col, candidate);
        } catch (Exception e) {
            this.errorValueChangedInCell(textfield, "catch (Exception e)");
            return;
        }
        
        System.out.println("SUCCESSS!!!!");
        textfield.setText(candidate.toString());
    }
    
    void errorValueChangedInCell(TextField textfield, String message) {

        Platform.runLater(() -> { 
            textfield.clear(); 
        });         
        System.out.println(message);
    }
}
