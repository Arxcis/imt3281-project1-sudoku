package no.ntnu.imt3281.sudoku;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class SudokuController {

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnExit;

    @FXML
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
    
    
    public static Parent makeSceneRoot() throws IOException {
		final String resourcePath = "./resources/fxml/sudoku.fxml";
		return FXMLLoader.load(SudokuController.class.getResource(resourcePath));
    }
}
