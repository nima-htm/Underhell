package characters;

import core.Character;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Alastor extends Character {
    private Image gifImage;
    private ImageView gifView;

    public Alastor() {
        super("Alastor", 300);

        gifImage = new Image(getClass().getResource("/animation/alastor_idle.gif").toExternalForm());
        gifView = new ImageView(gifImage);
        gifView.setFitWidth(300);
        gifView.setPreserveRatio(true);
        gifView.setLayoutX(200);
        gifView.setLayoutY(50);

    }
    public ImageView getView() {
        return gifView;
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
