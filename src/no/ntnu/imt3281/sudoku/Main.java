package no.ntnu.imt3281.sudoku;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    // @ref Stop() cleanup - https://stackoverflow.com/a/46060236 - 13.09.18    
    private final ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    public void stop() throws InterruptedException {
        System.out.println("Stop called: try to let background threads complete...");
        exec.shutdown();
        if (exec.awaitTermination(2, TimeUnit.SECONDS)) {
            System.out.println("Background threads exited");
        } else {
            System.out.println("Background threads did not exit, trying to force termination (via interruption)");
            exec.shutdownNow();
        }       
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
