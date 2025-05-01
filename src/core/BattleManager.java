package core;

import characters.Alastor;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.media.Media;
import javafx.util.Duration;

import javax.crypto.Cipher;

public class BattleManager {
    private Stage stage;
    private Scene battleScene;
    private Group root;
    private Circle playerHeart;
    private Rectangle battleBox;
    private MediaPlayer mediaPlayer;
    private ImageView heartView;
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
        playBattleMusic();
        setupPlayerHeart();
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
            musicPlayer.setStopTime(musicPlayer.getMedia().getDuration());
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

                if (activeKeys.contains(KeyCode.LEFT) && x - speed >= minX) {
                    playerHeart.setTranslateX(x - speed);
                    heartView.setTranslateX(x - speed -10);
                }


                if (activeKeys.contains(KeyCode.RIGHT) && x + speed <= maxX) {
                    playerHeart.setTranslateX(x + speed);
                    heartView.setTranslateX(x + speed - 10);
                }
                if (activeKeys.contains(KeyCode.UP) && y - speed >= minY) {
                    playerHeart.setTranslateY(y - speed);
                    heartView.setTranslateY(y - speed - 10);
                }
                if (activeKeys.contains(KeyCode.DOWN) && y + speed <= maxY) {
                    playerHeart.setTranslateY(y + speed);
                    heartView.setTranslateY(y + speed - 10);
                }
            }
        };
        movement.start();

    }

    private void setupEnemy() {
        enemy = new Alastor();
        root.getChildren().add(enemy.getView());
    }


    private void setupUI() {
        battleBox = new Rectangle(175, 400, 450, 200);
        battleBox.setStroke(Color.WHITE);
        battleBox.setStrokeWidth(8);
        battleBox.setFill(Color.BLACK);

        root.getChildren().add(battleBox);


        attackLayer = new Group();
        root.getChildren().add(attackLayer);
    }

    private void setupPlayerHeart() {
        playerHeart = new Circle(10, Color.GRAY);
        playerHeart.setTranslateX(400);
        playerHeart.setTranslateY(450);
        playerHeart.setVisible(true);

        heartView = new ImageView(new Image(getClass().getResource("/animation/player/heart.png").toExternalForm()));
        heartView.setFitWidth(20);
        heartView.setFitHeight(20);
        heartView.setTranslateX(playerHeart.getTranslateX() - 10);
        heartView.setTranslateY(playerHeart.getTranslateY() - 10);
        root.getChildren().addAll(playerHeart, heartView);

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
