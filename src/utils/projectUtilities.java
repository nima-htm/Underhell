package utils;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;

public class projectUtilities {
    private String soundPath;
    private Node enemy;
    public projectUtilities(Node enemy)
    {
        this.enemy = enemy;
    }

    public projectUtilities(String soundPath){
        if (soundPath == null || soundPath == "")
            throw new IllegalArgumentException("No sound sound !");
        this.soundPath = soundPath;
    }
    public void playHitSound() {
        try {
            URL soundUrl = getClass().getResource(soundPath);
            if (soundUrl != null) {
                AudioClip clip = new AudioClip(soundUrl.toExternalForm());
                clip.play();
            } else {
                System.out.println("Hit sound not found.");
            }
        } catch (Exception e) {
            System.out.println("Could not play hit sound: " + e.getMessage());
        }
    }

    public void shakeEnemy() {
        TranslateTransition shake = new TranslateTransition(Duration.seconds(0.1), enemy);
        shake.setByX(10);
        shake.setAutoReverse(true);
        shake.setCycleCount(4);
        shake.play();
    }

}
