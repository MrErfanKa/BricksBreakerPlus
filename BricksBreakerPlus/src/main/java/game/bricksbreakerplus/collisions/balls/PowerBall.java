package game.bricksbreakerplus.collisions.balls;

import game.bricksbreakerplus.Loader;

import java.net.MalformedURLException;

public class PowerBall extends Circle{
    public PowerBall(double x, double y) throws MalformedURLException {
        super(Loader.getPowerBall(), x, y, RAD);
    }
}
