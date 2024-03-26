package game.bricksbreakerplus.collisions.balls;

import game.bricksbreakerplus.Loader;

import java.net.MalformedURLException;

public class HeartBall extends Circle{
    public HeartBall(double x, double y) throws MalformedURLException {
        super(Loader.getHeartBall(), x, y, RAD);
    }
}
