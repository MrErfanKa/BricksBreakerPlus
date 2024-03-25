package game.bricksbreakerplus.collisions.balls;

import game.bricksbreakerplus.Loader;

import java.net.MalformedURLException;

public class SpeedBall extends Circle{
    public SpeedBall(double x, double y) throws MalformedURLException {
        super(Loader.getSpeedBall(), x, y, RAD);
    }
}
