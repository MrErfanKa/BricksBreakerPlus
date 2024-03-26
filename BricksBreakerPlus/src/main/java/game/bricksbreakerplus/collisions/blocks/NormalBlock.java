package game.bricksbreakerplus.collisions.blocks;

import javafx.scene.layout.Background;
import javafx.scene.layout.BorderImage;
import javafx.scene.paint.Color;

public class NormalBlock extends Block{
    public NormalBlock(String s, Color color) {
        super(s);
        setBackground(Background.fill(color));
    }
}
