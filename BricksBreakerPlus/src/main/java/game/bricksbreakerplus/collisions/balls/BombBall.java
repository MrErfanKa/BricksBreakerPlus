package game.bricksbreakerplus.collisions.balls;

import game.bricksbreakerplus.Loader;

import java.net.MalformedURLException;

public class BombBall extends Circle{
    public BombBall(double x, double y) throws MalformedURLException {
        super(Loader.getBombBall(), x, y, RAD);
    }
}
