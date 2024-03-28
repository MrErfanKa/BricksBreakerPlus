package game.bricksbreakerplus.collisions.balls;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class NormalBall extends Circle {
    public NormalBall(double x, double y, double radius, Paint fill) {
        super(radius, fill);
        set(x, y);
    }
    public void set(double x, double y){
        setTranslateX(x);
        setTranslateY(y);
        centerY = y + getRadius();
        centerX = x + getRadius();
    }
    private double centerX, centerY;
    public double getBallCenterX() {
        return centerX;
    }

    private double changeX = 1, changeY = 1;

    public double getChangeX() {
        return changeX;
    }

    public void setChangeX(double changeX) {
        this.changeX = changeX;
    }

    public double getChangeY() {
        return changeY;
    }

    public void setChangeY(double changeY) {
        this.changeY = changeY;
    }

    public double getBallCenterY() {
        return centerY;
    }

    public double getY(){
        return getTranslateY();
    }
    public double getX(){
        return getTranslateX();
    }

}
