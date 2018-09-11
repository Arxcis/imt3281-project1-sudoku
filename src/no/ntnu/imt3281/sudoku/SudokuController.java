package no.ntnu.imt3281.sudoku;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class SudokuController {
    
    public static Parent makeSceneRoot() throws IOException {
		final String resourcePath = "./resources/fxml/sudoku.fxml";
		return FXMLLoader.load(SudokuController.class.getResource(resourcePath));
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
        assert btnNewGame != null : "fx:id=\"btnNewGame\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnLoad != null : "fx:id=\"btnLoad\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert gridSudoku != null : "fx:id=\"gridSudoku\" was not injected: check your FXML file 'sudoku.fxml'.";
        
        for (int row = 0; row < this.gridSudoku.getRowCount(); ++row) {
        	for (int col = 0; col < this.gridSudoku.getColumnCount(); ++col) {
        		var rect = new Rectangle(44,42);
        		rect.setFill(Color.TRANSPARENT);
        		rect.setStrokeType(StrokeType.INSIDE);
        		rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(1);
            
        		this.gridSudoku.add(rect, col, row);
        	} 
        }
    }
}
