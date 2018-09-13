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
    public void start(Stage primaryStage) {
	
        Scene scene = null;
        try {
            scene = ViewController.loadScene();
        } catch(IOException e) {
            System.out.println("IOException: " + e.getCause());
            return; 
        }
        primaryStage.setTitle("Sudoku");
		primaryStage.setScene(scene);
		primaryStage.show();
    }

    // @ref Stop() cleanup - https://stackoverflow.com/a/46060236 - 13.09.18    
    private final ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    public void stop() throws InterruptedException {
        exec.shutdown();

        final boolean finishedInATimelyManner = exec.awaitTermination(2, TimeUnit.SECONDS);
        if (!finishedInATimelyManner) {
            exec.shutdownNow();
        }       
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
