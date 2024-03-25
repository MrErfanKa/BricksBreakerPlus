package game.bricksbreakerplus.collisions.balls;

public interface Circalable {
    void set(double x, double y);
    double distance(double x, double y);
    boolean inRange(double x, double y);
}
