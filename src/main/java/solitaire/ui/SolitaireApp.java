package solitaire.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SolitaireApp extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane(); // the GUI goes in here later
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Peg Solitaire");
        stage.show();
    }
}