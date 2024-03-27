package game.bricksbreakerplus.collisions.blocks;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public abstract class Block extends Label implements  RectangleAble{
    final static double WIDTH = 100, HEIGHT = 50, RAD = 20, VAR = 0.1;
    public Block(String s, double x, double y){
        super(s);
        init();
        set(x, y);
    }
    public void init(){
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    @Override
    public void set(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    @Override
    public double distance(double x, double y) {
        return 0;
    }

    @Override
    public boolean inRange(double x, double y) {
        return false;
    }
    @Override
    public boolean ballTouch(double x, double y, double rad) {
        if(y - rad + VAR <= getTranslateY() + HEIGHT && y + RAD - VAR >= getTranslateY())
            return true;
        else if(x - rad + VAR <= getTranslateX() + WIDTH && x + RAD - VAR >= getTranslateX())
            return true;
        return false;
    }

    @Override
    public boolean rectangleTouch(double x, double y) {
        return (Math.abs(getTranslateY() - y) <= HEIGHT - VAR && Math.abs(getTranslateX() - x) <= WIDTH - VAR);
    }

    @Override
    public boolean touch(Object o) {
        if(o instanceof javafx.scene.shape.Circle)
            return ballTouch(((Circle)o).getCenterX(), ((Circle)o).getCenterY(), ((Circle)o).getRadius());
        else if(o instanceof Block)
            return rectangleTouch(((Block)o).getTranslateX(), ((Block)o).getTranslateY());
        return false;
    }
}
