package game.bricksbreakerplus.collisions.blocks;

import game.bricksbreakerplus.Loader;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;

public class EarthquakeBlock extends Block{
    public EarthquakeBlock(String s, double x, double y) {
        super(s, x, y);
        Image image = new Image(new File(Loader.getEarthquake()).toURI().toString());
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(WIDTH, HEIGHT, true, true, true, true));
        setBackground(new Background(backgroundimage));
    }
}
