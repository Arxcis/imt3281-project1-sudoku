
package no.ntnu.imt3281.sudoku;

import java.util.ArrayList;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import no.ntnu.imt3281.language.LanguageBundler;

// @see https://github.com/TestFX/TestFX 11.10.18
public class SudokuControllerFxTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        LanguageBundler.init();
        Scene scene = null;
        scene = SudokuController.loadScene();
        stage.setScene(scene);
        stage.show();
        stage.toFront();
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