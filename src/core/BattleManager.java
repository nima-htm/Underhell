package core;

import javafx.animation.*;
import javafx.geometry.Bounds;
import utils.projectUtilities;
import characters.Alastor;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private Group playerOptions;
    private Circle playerHeart;
    private Rectangle battleBox;
    private MediaPlayer mediaPlayer;
    private ImageView heartView;
    private Alastor enemy;
    private Group attackLayer;
    private List<Node> attacks = new ArrayList<>();
    private AnimationTimer attackLoop;
    private Text playerDialogue;
    private StackPane enemyDialogueBox;
    private Text enemyDialogueText;
    private int dialogueStep = 0;
    private boolean playerTurn = false;

    public BattleManager(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.battleScene = new Scene(root, 800, 650, Color.BLACK);
        setupUI();
        playBattleMusic();
        setupPlayerHeart();
        setupEnemy();
        setupEnemyHPBar();
        setupPlayerOptions();
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

    private void handleDialogueProgression() {

        if (dialogueStep == 1) {
            playerDialogue.setVisible(false);
            enemyDialogueText.setText(enemy.getNextDialogue());
            enemyDialogueBox.setVisible(true);
            dialogueStep = 2;
        } else if (dialogueStep == 2) {
            enemyDialogueBox.setVisible(false);
            startPlayerTurn();
            dialogueStep = 0;
        }
    }


    private void setupPlayerMovements() {
        battleScene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            activeKeys.add(code);

            if (dialogueStep > 0 && code == KeyCode.ENTER) {
                handleDialogueProgression();
            }
        });

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
                    heartView.setTranslateX(x - speed - 10);
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


        enemyDialogueText = new Text();
        enemyDialogueText.setFill(Color.BLACK);
        enemyDialogueText.setStyle("-fx-font-size: 18px; -fx-font-family: 'monospace'; -fx-font-weight: bold;");

        TextFlow textFlow = new TextFlow(enemyDialogueText);
        textFlow.setMaxWidth(200);
        textFlow.setPadding(new Insets(10));
        textFlow.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        textFlow.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));

        enemyDialogueBox = new StackPane(textFlow);
        enemyDialogueBox.setVisible(false);


        enemyDialogueBox.setLayoutX(enemy.getView().getLayoutX() + 520);
        enemyDialogueBox.setLayoutY(enemy.getView().getLayoutY() + 40);

        root.getChildren().add(enemyDialogueBox);

    }


    private void setupUI() {
        battleBox = new Rectangle(175, 350, 450, 200);
        battleBox.setStroke(Color.WHITE);
        battleBox.setStrokeWidth(8);
        battleBox.setFill(Color.BLACK);
        root.getChildren().add(battleBox);
        attackLayer = new Group();
        root.getChildren().add(attackLayer);
        Font font = Font.font("Courier New", FontWeight.BOLD, 20);
        playerDialogue = new Text();
        playerDialogue.setFill(Color.WHITE);
        playerDialogue.setFont(font);
        playerDialogue.setWrappingWidth(440);
        playerDialogue.setTranslateX(battleBox.getX() + 20);
        playerDialogue.setTranslateY(battleBox.getY() + 40);
        playerDialogue.setVisible(false);
        root.getChildren().add(playerDialogue);
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

    private void setupPlayerOptions() {
        playerOptions = new Group();

        String[] labels = {"Fight", "Talk", "Items"};
        for (int i = 0; i < 3; i++) {
            Rectangle option_box = new Rectangle(140, 40);
            option_box.setStroke(Color.WHITE);
            option_box.setStrokeWidth(3);
            option_box.setTranslateX(175 + i * 155);
            option_box.setTranslateY(570);
            Font font = Font.font("Courier New");
            Text label = new Text(labels[i]);
            if (labels[i].equals("Talk")) {
                option_box.setOnMouseClicked(e -> startDialogueSequence());
            }
            if (labels[i].equals("Fight")) {
                option_box.setOnMouseClicked(e -> playAttackAnimation(() -> {
                    applyDamageToEnemy(7 + (int) (Math.random() * ((15 - 7) + 1)));
                }));

            }
            label.setFont(font);
            label.setFill(Color.WHITE);
            label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            label.setTranslateX(option_box.getTranslateX() + 50);
            label.setTranslateY(option_box.getTranslateY() + 25);

            playerOptions.getChildren().addAll(option_box, label);
        }
        root.getChildren().add(playerOptions);
    }

    private void startDialogueSequence() {
        playerHeart.setVisible(false);
        playerOptions.setVisible(false);
        playerDialogue.setText("You: Let me go ...");
        playerDialogue.setVisible(true);
        dialogueStep = 1;
    }

    private void startPlayerTurn() {
        playerTurn = true;
        playerHeart.setVisible(true);
        playerOptions.setVisible(true);
    }

    private void playAttackAnimation(Runnable onFinish) {
        new projectUtilities(enemy.getView()).shakeEnemy();

        try {
            new projectUtilities("/sounds/hit_sound.mp3").playHitSound();
        } catch (Exception ex) {
            System.out.println("Could not play hit sound: " + ex.getMessage());
        }
        Bounds enemyBounds = enemy.getView().localToScene(enemy.getView().getBoundsInLocal());

        double centerX = (enemyBounds.getMinX() + enemyBounds.getMaxX()) / 2;
        double centerY = (enemyBounds.getMinY() + enemyBounds.getMaxY()) / 2;

        Line line1 = new Line(-30, -30, 30, 30);
        Line line2 = new Line(30, -30, -30, 30);

        line1.setStroke(Color.RED);
        line2.setStroke(Color.RED);
        line1.setStrokeWidth(5);
        line2.setStrokeWidth(5);

        Group redCross = new Group(line1, line2);
        redCross.setLayoutX(centerX);
        redCross.setLayoutY(centerY);
        root.getChildren().add(redCross);
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.3), redCross);
        scale.setFromX(0.2);
        scale.setFromY(0.2);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);

        scale.setOnFinished(e -> {
            root.getChildren().remove(redCross);
            onFinish.run();
        });

        scale.play();
    }


    private Rectangle enemyHPBarBack;
    private Rectangle enemyHPBarFront;

    private void setupEnemyHPBar() {
        enemyHPBarBack = new Rectangle(200, 20, Color.DARKRED);
        enemyHPBarFront = new Rectangle(200, 20, Color.LIMEGREEN);

        enemyHPBarBack.setLayoutX(enemy.getView().getLayoutX() + 30);
        enemyHPBarBack.setLayoutY(enemy.getView().getLayoutY() - 30);
        enemyHPBarFront.setLayoutX(enemyHPBarBack.getLayoutX());
        enemyHPBarFront.setLayoutY(enemyHPBarBack.getLayoutY());

        root.getChildren().addAll(enemyHPBarBack, enemyHPBarFront);

    }

    private void applyDamageToEnemy(int damage) {
        double currentWidth = enemyHPBarFront.getWidth();
        double newWidth = Math.max(0, currentWidth - damage);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(enemyHPBarFront.widthProperty(), newWidth, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

}

