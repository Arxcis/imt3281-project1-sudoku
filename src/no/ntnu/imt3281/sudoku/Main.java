package no.ntnu.imt3281.sudoku;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import no.ntnu.imt3281.language.LanguageBundler;

public class Main extends Application {
    /**
     * Helper in stop() to shut down threads
     */
    final ExecutorService exec = Executors.newCachedThreadPool();

    /**
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        LanguageBundler.init();
        Scene scene = null;
        scene = SudokuController.loadScene();
        SudokuController.setStage(primaryStage);
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    Platform.exit();
                }
            }
        });


    }

    /**
     * Overriden stop function that properly shuts down the background threads.
     * @see https://bitbucket.org/jonasjso/prosjekt-1-imt3281-h2018/issues/34/background-thread-leaks-every-time
     * @see https://stackoverflow.com/a/46060236 - 13.09.18
     */
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
