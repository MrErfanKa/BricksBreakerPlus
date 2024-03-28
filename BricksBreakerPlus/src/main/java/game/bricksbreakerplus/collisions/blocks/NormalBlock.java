package game.bricksbreakerplus.collisions.blocks;

import javafx.scene.layout.Background;
import javafx.scene.layout.BorderImage;
import javafx.scene.paint.Color;

public class NormalBlock extends Block{
    private Color color;
    public NormalBlock(String s, Color color, double x, double y) {
        super(s, x, y);
        setColor(color);
    }
    public void setColor(Color color){
        setBackground(Background.fill(color));
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    public double getRed(){
        return color.getRed();
    }
    public double getBlue(){
        return color.getBlue();
    }
    public double getGreen(){
        return color.getGreen();
    }
}
