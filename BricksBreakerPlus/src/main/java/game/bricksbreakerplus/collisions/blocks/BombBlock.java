package game.bricksbreakerplus.collisions.blocks;

import game.bricksbreakerplus.Loader;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;

public class BombBlock extends Block{
    public BombBlock(String s, double x, double y) {
        super(s, x, y);
        Image image = new Image(new File(Loader.getBombBlock()).toURI().toString());
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(WIDTH, HEIGHT, true, true, true, true));
        setBackground(new Background(backgroundimage));
    }
    public double getCenterX(){
        return getTranslateX() + WIDTH / 2;
    }
    public double getCenterY(){
        return getTranslateY() + HEIGHT / 2;
    }
}
