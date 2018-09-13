package no.ntnu.imt3281.sudoku;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
	@Override
    public void start(Stage primaryStage) throws IOException {
		
		final var root = SudokuController.makeSceneRoot();
		final Scene scene = new Scene(root);
		primaryStage.setTitle("Sudoku");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
