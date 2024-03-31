package game.bricksbreakerplus;

import game.bricksbreakerplus.collisions.balls.*;
import game.bricksbreakerplus.collisions.blocks.BombBlock;
import game.bricksbreakerplus.collisions.blocks.DanceBlock;
import game.bricksbreakerplus.collisions.blocks.EarthquakeBlock;
import game.bricksbreakerplus.collisions.blocks.NormalBlock;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Board {
    private boolean additionalHeart = false;
    private boolean showArrow = true;

    public static Color ballColor = Color.BLACK;

    public static Color getBallColor() {
        return ballColor;
    }

    private String name, difficulty;
    private static int increase = 1;
    private static int minNum = 1;
    private static int maxNum = 5;
    private static double bownd = 5;

    private final double WIDTH = 100, HEIGHT = 50, RAD = 20, NORMALBALLRAD = 5;




    public double getNORMALBALLRAD() {
        return NORMALBALLRAD;
    }

    public double getWIDTH() {
        return WIDTH;
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    public double getRAD() {
        return RAD;
    }

    private double x, y;


    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }

    public void setShapes(ArrayList<Pair<Node, Boolean>> shapes) {
        this.shapes = shapes;
    }

    public void newBoard() {
        y = 700 - NORMALBALLRAD * 2;
        x = Math.random() * (600 - RAD * 2);
        x += RAD;
    }

    public Board(String difficulty, String name, boolean showArrow) {
        newBoard();

        this.name = name;
        this.showArrow = showArrow;
        shapes = new ArrayList<>();
        this.difficulty = difficulty;
        minNum = 1;
        changeMode(difficulty);
    }
    public void changeMode(String difficulty){
        if(difficulty.equals("easy")){
            blockRandom = 0;
            danceBlockRandom = 24;
            earthquakeRandom = 25.5;
            bombRandom = 25.5;
            additionalBallRandom = 26;
            heartRandom = 28;
            powerBallRandom = 28;
            speedBallRandom = 29;
            nothingRandom = 30;
//            blockRandom = 0;
//            danceBlockRandom = 0;
//            earthquakeRandom = 0;
//            bombRandom = 0;
//            additionalBallRandom = 15;
//            heartRandom = 15;
//            powerBallRandom = 15;
//            speedBallRandom = 15;
//            nothingRandom = 30;

        }
        else if(difficulty.equals("normal")){
            blockRandom = 0;
            danceBlockRandom = 35;
            earthquakeRandom = 36.2;
            bombRandom = 36.2;
            additionalBallRandom = 37;
            heartRandom = 38.4;
            powerBallRandom = 38.4;
            speedBallRandom = 39.2;
            nothingRandom = 40;
        }
        else{
            blockRandom = 0;
            danceBlockRandom = 41;
            earthquakeRandom = 42.4;
            bombRandom = 42.4;
            additionalBallRandom = 43;
            heartRandom = 44;
            powerBallRandom = 44;
            speedBallRandom = 44.5;
            nothingRandom = 45;
        }
    }

    private double blockRandom, danceBlockRandom, earthquakeRandom, bombRandom, additionalBallRandom, heartRandom, powerBallRandom, speedBallRandom, nothingRandom;
    private ArrayList<Pair<Node, Boolean>> shapes;

//    public Board(boolean additionalHeart, double blockRandom, double bombRandom, double additionalBallRandom, double heartRandom, double powerBallRandom, double speedBallRandom, double nothingRandom, ArrayList<Pair<Object, Boolean>> shapes) {
//        this.additionalHeart = additionalHeart;
//        this.blockRandom = blockRandom;
//        this.bombRandom = bombRandom;
//        this.additionalBallRandom = additionalBallRandom;
//        this.heartRandom = heartRandom;
//        this.powerBallRandom = powerBallRandom;
//        this.speedBallRandom = speedBallRandom;
//        this.nothingRandom = nothingRandom;
//        this.shapes = (shapes == null ? new ArrayList<>() : shapes);
//    }



    // init() for difficulty

    public void addNewLayer() throws MalformedURLException {
        double x = 0, y = 0;
        for(int i = 0; i < 6; i++){
            x = WIDTH * i;
            double rand = Math.random() * 100;
            Node o;
            if(rand >= nothingRandom)continue;
            else if(rand < nothingRandom && rand >= speedBallRandom){
                o = new SpeedBall(x, y);
                shapes.add(new Pair<>(o, false));
            }
            else if(rand < speedBallRandom && rand >= powerBallRandom){
                o = new PowerBall(x, y);
                shapes.add(new Pair<>(o, false));
            }
            else if(rand < powerBallRandom && rand >= heartRandom){
                if(isAdditionalHeart())
                    continue;
                o = new HeartBall(x, y);
                shapes.add(new Pair<>(o, false));
            }
            else if(rand < heartRandom && rand >= additionalBallRandom && !additionalHeart){
                o = new AdditionalBall(x, y);
                shapes.add(new Pair<>(o, false));
                additionalHeart = true;
            }
            else if(rand < additionalBallRandom && rand >= bombRandom){
                o = new BombBlock(String.valueOf(getNewNumber()), x, y);
                shapes.add(new Pair<>(o, false));
            }
            else if(rand < bombRandom && rand >= earthquakeRandom){
                o = new EarthquakeBlock(String.valueOf(getNewNumber()), x, y);
                shapes.add(new Pair<>(o, false));
            }
            else if(rand < earthquakeRandom && rand >= danceBlockRandom){
                o = new DanceBlock(String.valueOf(getNewNumber()), x, y);
                shapes.add(new Pair<>(o, false));
            }
            else if(rand < danceBlockRandom && rand >= blockRandom){
                o = new NormalBlock(String.valueOf(getNewNumber()), getNewColor(), x, y);
                shapes.add(new Pair<>(o, false));
            }
        }
        if(difficulty.equals("easy")){
            minNum += 1;
        }
        else if(difficulty.equals("normal")){
            minNum += 2;
        }
        else{
            minNum += 3;
        }
    }
    double blueRandom, redRandom, yellowRandom, greenRandom, cyanRandom;
    public Color getNewColor(){
        return Color.CYAN;
    }
    public int getNewNumber(){
        int rand = (int)(Math.floor(Math.random() * bownd)) + minNum;
        return rand;
    }

    public void setAdditionalHeart(boolean additionalHeart) {
        this.additionalHeart = additionalHeart;
    }

    public boolean isAdditionalHeart() {
        return additionalHeart;
    }

    public ArrayList<Pair<Node, Boolean>> getShapes() {
        return shapes;
    }

    public boolean isShowArrow() {
        return showArrow;
    }

    public void setShowArrow(boolean showArrow) {
        this.showArrow = showArrow;
    }
}
