
package no.ntnu.imt3281.sudoku;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

// @see https://github.com/TestFX/TestFX 11.10.18
public class SudokuControllerFxTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Language.init();
        Scene scene = null;
        scene = SudokuController.loadScene();
        stage.setScene(scene);
    }


    @Test
    public void testMakeTextGrid () {
        ArrayList<ArrayList<TextField>> textGrid = SudokuController.makeTextGrid();
    }
    
    @Test
    public void testBindTextGridToParentPane() {
        ArrayList<ArrayList<TextField>> textGrid = SudokuController.makeTextGrid();
        GridPane parentPane = new GridPane();
        SudokuController.bindTextGridToParent(textGrid, parentPane);
    }
}