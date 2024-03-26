package game.bricksbreakerplus.collisions.blocks;

import game.bricksbreakerplus.Loader;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;

public class DanceBlock extends Block{
    public DanceBlock(String s) {
        super(s);
        Image image = new Image(new File(Loader.getRainbow()).toURI().toString());
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(WIDTH, HEIGHT, true, true, true, true));
        setBackground(new Background(backgroundimage));
    }
}
