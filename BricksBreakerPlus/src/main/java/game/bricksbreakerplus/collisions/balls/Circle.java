package game.bricksbreakerplus.collisions.balls;

import game.bricksbreakerplus.collisions.blocks.Block;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

import java.io.File;
import java.net.MalformedURLException;

public abstract class Circle extends ImageView implements CircleAble {

    final static double WIDTH = 100, HEIGHT = 50, RAD = 20, VAR = 0.1;
    public Circle(String url, double x, double y, double rad) throws MalformedURLException {
        super(new File(url).toURI().toString());
        this.rad = rad;
        set(x, y);
        centerX = x + rad;
        centerY = y + rad;
        setFitHeight(RAD);
        setFitWidth(RAD);
    }
    private double rad = 0, centerX, centerY;
    @Override
    public void set(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    public double getRad() {
        return rad;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    @Override
    public double distance(double x, double y) {
        return Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
    }

    @Override
    public boolean inRange(double x, double y) {
        return distance(x, y) <= rad - VAR;
    }

    @Override
    public boolean ballTouch(double x, double y, double rad) {
        return Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)) <= rad + this.rad - VAR;
    }

    @Override
    public boolean rectangleTouch(double x, double y) {
        if(getCenterY() - getRad() + VAR <= y + HEIGHT && getCenterY() + RAD - VAR >= y)
            return true;
        else if(getCenterX() - getRad() + VAR <= x + WIDTH && getCenterX() + RAD - VAR >= x)
            return true;
        return false;
    }

    @Override
    public boolean touch(Object o) {
        if(o instanceof Circle)
            return ballTouch(((javafx.scene.shape.Circle)o).getCenterX(), ((javafx.scene.shape.Circle)o).getCenterY(), ((javafx.scene.shape.Circle)o).getRadius());
        else if(o instanceof Block)
            return rectangleTouch(((Block)o).getTranslateX(), ((Block)o).getTranslateY());
        return false;
    }
}
