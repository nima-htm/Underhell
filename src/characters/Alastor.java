package characters;

import core.Character;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;



public class Alastor extends Character {
    private final long DEFAULT_DELAY = 150_000_000;
    private int currentFrame = 0;
    private long lastFrameTime = 0;


    private Image[] frames;
    private ImageView view;
    private Timeline animation;
    private AnimationTimer animationTimer;

    public Alastor() {
        super("Alastor", 300);

        frames = new Image[] {
                new Image(getClass().getResource("/animation/alastor/1.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/1.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/1.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/1.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/1.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/1.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/2.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/3.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/4.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/5.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/6.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/7.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/8.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/8.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/8.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/8.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/8.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/8.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/9.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/10.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/11.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/12.PNG").toExternalForm()),
                new Image(getClass().getResource("/animation/alastor/13.PNG").toExternalForm()),
        };

        view = new ImageView(frames[0]);
        view.setFitWidth(320);
        view.setFitHeight(320);
        view.setTranslateX(235);
        view.setTranslateY(15);

        startAnimation();
    }
    public ImageView getView() {
        return view;
    }
    public void startAnimation() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long delay = getDelayForFrame(currentFrame);

                if (now - lastFrameTime >= delay) {
                    currentFrame = (currentFrame + 1) % frames.length;
                    view.setImage(frames[currentFrame]);
                    lastFrameTime = now;
                }
            }
        };
        animationTimer.start();
    }
    private long getDelayForFrame(int frameIndex) {
        return switch (frameIndex) {
            case 0, 10 -> 3_000_000_000L;
            default -> DEFAULT_DELAY;
        };
    }

    @Override
    public void weakAttack() {
        System.out.println("Alastor used Radio Zap!");
    }

    @Override
    public void mediumAttack() {
        System.out.println("Alastor used Tentacle Slam!");
    }

    @Override
    public void strongAttack() {
        System.out.println("Alastor used Hellfire Burst!");
    }
}
