package game.bricksbreakerplus.collisions.balls;

import game.bricksbreakerplus.Loader;

import java.net.MalformedURLException;

public class AdditionalBall extends Circle{
    public AdditionalBall(double x, double y) throws MalformedURLException {
        super(Loader.getAdditionalBall(), x, y, RAD);
    }
}
