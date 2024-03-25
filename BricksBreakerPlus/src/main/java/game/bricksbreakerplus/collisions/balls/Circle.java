package game.bricksbreakerplus.collisions.balls;

import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public abstract class Circle extends ImageView implements Circalable{

    static final double RAD = 20;
    public Circle(String url, double x, double y, double rad) throws MalformedURLException {
        super(String.valueOf(new File(url).toURI().toURL()));
        this.rad = rad;
        set(x, y);
        centerX = x + rad;
        centerY = y + rad;
        setFitHeight(RAD);
        setFitWidth(RAD);
    }
    double rad = 0, centerX, centerY;
    @Override
    public void set(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    @Override
    public double distance(double x, double y) {
        return Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
    }

    @Override
    public boolean inRange(double x, double y) {
        return distance(x, y) <= rad - 0.1;
    }
}
