package game.bricksbreakerplus.collisions.blocks;

import game.bricksbreakerplus.collisions.balls.NormalBall;

public interface RectangleAble {
    void set(double x, double y);
    double distance(double x, double y);
    boolean inRange(double x, double y);
    boolean ballTouch(double x, double y, double rad);
    boolean rectangleTouch(double x, double y);
    boolean touch(Object o);

    boolean touchLeft(NormalBall normalBall);
    boolean touchRight(NormalBall normalBall);
    boolean touchUp(NormalBall normalBall);
    boolean touchDown(NormalBall normalBall);
}
