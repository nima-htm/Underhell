package core;

import characters.Alastor;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.media.Media;
import javafx.util.Duration;

public class BattleManager {
    private Stage stage;
    private Scene battleScene;
    private Group root;
    private Circle playerHeart;
    private Rectangle battleBox;
    private MediaPlayer mediaPlayer;

    private Alastor enemy;
    private Group attackLayer;
    private List<Node> attacks = new ArrayList<>();
    private AnimationTimer attackLoop;

    private boolean playerTurn = false;

    public BattleManager(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.battleScene = new Scene(root, 800, 650, Color.BLACK);



        setupUI();
        setupPlayerHeart();
        playBattleMusic();
        setupEnemy();
        setupControls();
        setupPlayerMovements();
        stage.setScene(battleScene);
        stage.setTitle("What are you scared of?");
        root.requestFocus();

    }

    private void playBattleMusic() {
        try {
            Media music = new Media(getClass().getResource("/sounds/BattleTheme.m4a").toExternalForm());
            MediaPlayer musicPlayer = new MediaPlayer(music);
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.setStartTime(Duration.seconds(3));
            mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());
            musicPlayer.play();
        } catch (Exception e) {
            System.out.println("Could not play music: " + e.getMessage());
        }
    }

    private final Set<KeyCode> activeKeys = new HashSet<>();

    private void setupControls() {
        battleScene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        battleScene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));
    }

    private void setupPlayerMovements() {
        AnimationTimer movement = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double x = playerHeart.getTranslateX();
                double y = playerHeart.getTranslateY();
                final double speed = 4;

                double minX = battleBox.getX() + 15;
                double maxX = battleBox.getX() + battleBox.getWidth() - 15;
                double minY = battleBox.getY() + 15;
                double maxY = battleBox.getY() + battleBox.getHeight() - 15;

                if (activeKeys.contains(KeyCode.A) && x - speed >= minX)
                    playerHeart.setTranslateX(x - speed);
                if (activeKeys.contains(KeyCode.D) && x + speed <= maxX)
                    playerHeart.setTranslateX(x + speed);
                if (activeKeys.contains(KeyCode.W) && y - speed >= minY)
                    playerHeart.setTranslateY(y - speed);
                if (activeKeys.contains(KeyCode.S) && y + speed <= maxY)
                    playerHeart.setTranslateY(y + speed);
            }
        };
        movement.start();

    }

    private void setupEnemy() {
        enemy = new Alastor();
        root.getChildren().add(enemy.getView());
    }


    private void setupUI() {
        battleBox = new Rectangle(200, 300, 400, 300);
        battleBox.setStroke(Color.WHITE);
        battleBox.setStrokeWidth(8);
        battleBox.setFill(Color.BLACK);

        root.getChildren().add(battleBox);


        attackLayer = new Group();
        root.getChildren().add(attackLayer);
    }

    private void setupPlayerHeart() {
        playerHeart = new Circle(10, Color.RED);
        playerHeart.setTranslateX(400);
        playerHeart.setTranslateY(450);
        root.getChildren().add(playerHeart);
    }

    private void endTurn() {
        playerTurn = !playerTurn;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            Platform.runLater(this::setupUI);
        }).start();
    }
}
