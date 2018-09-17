package no.ntnu.imt3281.sudoku;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


public class ViewController {

    public static Scene loadScene() throws IOException {
        final URL fxml = ViewController.class.getResource("View.fxml");
        final Parent root = FXMLLoader.load(fxml);
        final Scene scene = new Scene(root);

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
    private GridPane grid;
    
    
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
    
    private ArrayList<ArrayList<TextField>> gridCells;
    
    @FXML
    void initialize() {
        // 1. Assert that all references are bound
        assert btnNewGame != null : "fx:id=\"btnNewGame\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnLoad != null : "fx:id=\"btnLoad\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'sudoku.fxml'.";
        assert grid != null : "fx:id=\"grid\" was not injected: check your FXML file 'View.fxml'.";
        
        this.gridCells = new ArrayList<>();
        
        Integer count = 0;
        for (int col = 0; col < Sudoku.COL_SIZE; ++col) {
            gridCells.add(new ArrayList<>());
            for (int row = 0; row < Sudoku.ROW_SIZE; ++row) {
                
                TextField tf = new TextField((count++).toString());
                AnchorPane.setTopAnchor(tf, 0.0);
                AnchorPane.setLeftAnchor(tf, 0.0);
                AnchorPane.setRightAnchor(tf,0.0);
                AnchorPane.setBottomAnchor(tf, 0.0);
                
                AnchorPane ap = new AnchorPane();
                ap.setMinSize(0, 0);
                ap.setPrefSize(50, 50);
                ap.setMaxSize(200, 200);
                ap.getChildren().add(tf);
                
                grid.add(ap, col, row);
                // Keep references to cells for easy access later
                gridCells.get(col).add(tf); 
            }
        }
    }
}
