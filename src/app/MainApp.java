package app;

import core.BattleManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Button startButton = new Button("START");
        startButton.setMaxSize(200,200);
        Font font = Font.font("Courier New", FontWeight.BOLD, 36);
        startButton.setFont(font);

        final String IDLE_BUTTON_STYLE = "-fx-border-color: #FFFFFF;-fx-text-fill: #FFFFFF; -fx-border-width: 5px; -fx-background-color: #000000;";
        final String HOVERED_BUTTON_STYLE = "-fx-border-color: #000000;-fx-text-fill: #000000; -fx-border-width: 5px; -fx-background-color: #FFFFFF;";

        startButton.setStyle(IDLE_BUTTON_STYLE);
        startButton.setStyle(IDLE_BUTTON_STYLE);
        startButton.setOnMouseEntered(e -> startButton.setStyle(HOVERED_BUTTON_STYLE));
        startButton.setOnMouseExited(e -> startButton.setStyle(IDLE_BUTTON_STYLE));

        StackPane root = new StackPane(startButton);
        root.setStyle("-fx-background-color: #000000");
        Scene menuScene = new Scene(root, 800, 650, Color.DARKRED);

        // process the stage
        startButton.setOnAction(e -> new BattleManager(stage));

        stage.setScene(menuScene);
        stage.setTitle("UnderHell \\_/");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
