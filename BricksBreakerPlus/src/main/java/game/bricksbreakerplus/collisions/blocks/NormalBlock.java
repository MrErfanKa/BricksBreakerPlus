package game.bricksbreakerplus.collisions.blocks;

import javafx.scene.layout.Background;
import javafx.scene.layout.BorderImage;
import javafx.scene.paint.Color;

public class NormalBlock extends Block{
    public NormalBlock(String s, Color color, double x, double y) {
        super(s, x, y);
        setBackground(Background.fill(color));
    }
    public void setColor(Color color){
        setBackground(Background.fill(color));
    }
}
