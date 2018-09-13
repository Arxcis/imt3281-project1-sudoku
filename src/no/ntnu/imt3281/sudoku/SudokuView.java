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
import javafx.scene.text.TextAlignment;

public class SudokuView {
    
    public static Scene loadScene() throws IOException
    { 
		final URL fxml = SudokuView.class.getResource("./resources/fxml/sudoku.fxml");
		final Parent root = FXMLLoader.load(fxml);
		final Scene scene = new Scene(root);
		
		final String css = SudokuView.class.getResource("./resources/fxml/sudoku.css").toString();
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
        // 1. Assert that all references are bound
        assert btnNewGame != null : "fx:id=\"btnNewGame\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnLoad != null : "fx:id=\"btnLoad\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'sudoku.fxml'.";

        // DOC - PseudoClass https://stackoverflow.com/a/34225599 - 12.09.18
        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");
    }
}
