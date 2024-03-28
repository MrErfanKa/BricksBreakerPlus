package game.bricksbreakerplus.collisions.balls;

import game.bricksbreakerplus.Loader;

import java.net.MalformedURLException;

public class Sargije extends Circle{
    public Sargije(double x, double y) throws MalformedURLException {
//        super(Loader.getBombBall(), x, y, RAD);
        super(Loader.getBombBlock(), x, y, RAD);
    }
}
