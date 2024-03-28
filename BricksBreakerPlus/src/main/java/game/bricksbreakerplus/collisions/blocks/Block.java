package game.bricksbreakerplus.collisions.blocks;

import game.bricksbreakerplus.collisions.balls.NormalBall;
import javafx.css.Style;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public abstract class Block extends Label implements  RectangleAble{
    final static double WIDTH = 100, HEIGHT = 50, RAD = 20, VAR = 0.1, ERROR = 4;
    public Block(String s, double x, double y){
        super(s);
        init();
        set(x + 1, y + 1);
        setAlignment(Pos.CENTER);
        setFont(new Font("Bold", 15));
    }
    public void init(){
        setPrefWidth(WIDTH - 2);
        setPrefHeight(HEIGHT - 2);
    }

    @Override
    public void set(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    @Override
    public double distance(double x, double y) {
        return 0;
    }

    @Override
    public boolean inRange(double x, double y) {
        return false;
    }
    @Override
    public boolean ballTouch(double x, double y, double rad) {
//        System.out.println(y - rad + VAR <= getTranslateY() + HEIGHT && y + RAD - VAR >= getTranslateY());
//        System.out.println(x - rad + VAR <= getTranslateX() + WIDTH && x + RAD - VAR >= getTranslateX());
//        System.out.println(x + " " + y + " " + rad + " " + getTranslateX() + " " + getTranslateY());
        return (y - rad + VAR <= getTranslateY() + ERROR + HEIGHT && y + rad - VAR >= getTranslateY() + ERROR &&
                x - rad + VAR <= getTranslateX() + ERROR + WIDTH && x + rad - VAR >= getTranslateX() + ERROR);
    }

    @Override
    public boolean rectangleTouch(double x, double y) {
        return (Math.abs(getTranslateY() - y) <= HEIGHT - VAR && Math.abs(getTranslateX() - x) <= WIDTH - VAR);
    }

    @Override
    public boolean touch(Object o) {
        if(o instanceof NormalBall)
            return ballTouch(((NormalBall)o).getBallCenterX(), ((NormalBall)o).getBallCenterY(), ((NormalBall)o).getRadius());
        else if(o instanceof Block)
            return rectangleTouch(((Block)o).getTranslateX(), ((Block)o).getTranslateY());
        return false;
    }
}
