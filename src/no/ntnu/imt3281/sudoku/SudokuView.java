package no.ntnu.imt3281.sudoku;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class SudokuView {
    
    public static Scene loadScene() throws IOException
        {
        
		final URL fxml = SudokuView.class
		        .getResource("./resources/fxml/sudoku.fxml");
		
		final Parent root = FXMLLoader.load(fxml);
		final Scene scene = new Scene(root);
		
		final String css = SudokuView.class
		        .getResource("./resources/fxml/sudoku.css")
		        .toExternalForm();
		
		scene.getStylesheets().add(css);
		return scene;
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnNewGame"
    private Button btnNewGame;

    @FXML // fx:id="btnSave"
    private Button btnSave;

    @FXML // fx:id="btnLoad"
    private Button btnLoad;

    @FXML // fx:id="btnExit"
    private Button btnExit;

    @FXML // fx:id="gridSudoku"
    private GridPane gridSudoku;

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
    void OnMousePressedGrid(MouseEvent event) {
    	System.out.println("OnMousePressedGrid");
    }

    @FXML
    void initialize() {
        // Assert that all references are bound
        assert btnNewGame != null : "fx:id=\"btnNewGame\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnLoad != null : "fx:id=\"btnLoad\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert gridSudoku != null : "fx:id=\"gridSudoku\" was not injected: check your FXML file 'sudoku.fxml'.";

        // @doc PseudoClass https://stackoverflow.com/a/34225599 - 12.09.18
        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");
        
        // Generate sudoku grid cells
        for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
        	for (int col = 0; col < Sudoku.COL_SIZE; ++col) {
        	    AnchorPane cell = new AnchorPane();
        	    cell.getStyleClass().add("cell");
                    cell.pseudoClassStateChanged(right, col == 2 || col == 5);
        	    cell.pseudoClassStateChanged(bottom, row == 2 || row == 5);
        	     
        	    Label label = new Label(Integer.toString(Sudoku.EMPTY_CELL));
        	    label.getStyleClass().add("label");
        	    cell.getChildren().add(label);
        	    this.gridSudoku.add(cell, col, row);
        	} 
        }
    }
}
