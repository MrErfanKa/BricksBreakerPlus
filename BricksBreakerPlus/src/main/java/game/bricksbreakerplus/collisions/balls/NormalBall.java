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
    }
    public double getY(){
        return getTranslateY();
    }
    public double getX(){
        return getTranslateX();
    }

}
