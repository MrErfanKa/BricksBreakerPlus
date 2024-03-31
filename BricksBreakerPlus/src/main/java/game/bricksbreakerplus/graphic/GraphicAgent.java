package game.bricksbreakerplus.graphic;

import game.bricksbreakerplus.Board;
import game.bricksbreakerplus.collisions.balls.*;
import game.bricksbreakerplus.collisions.blocks.Block;
import game.bricksbreakerplus.collisions.blocks.BombBlock;
import game.bricksbreakerplus.collisions.blocks.DanceBlock;
import game.bricksbreakerplus.collisions.blocks.NormalBlock;
import game.bricksbreakerplus.media.SoundLoader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphicAgent{
    public static Stage primaryStage;
    private Group group;
    private Scene scene;
    private NormalBall mainBall;
    private int hearts = 1;
    private static String difficulty = "easy";
    private final double DISTANCE = 10, SHADOWINT = 0.3, STANDARDDISS = 20, VAR = 0.5;
    private final double BOMBRATE = 200;
    private int score = 0, hitBalls;
    private Board board;
    private final double UPPAGE = 100, DOWNPAGE = 700;
    public static void switchScene(Scene scene){
        primaryStage.setScene(scene);
    }


    private Label time, remainBalls, timeString, scoreLabel, nameLabel;
    private double downRate = 0.4, addNewThings = 100 / downRate, ballRate = 3.5, defaultBallRate = 3.5, ballGo = 1;
    private int checkToAdd = 0;

    private SoundLoader soundLoader;
    private double mouseX, mouseY;

    Timeline changeTime = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        time.setText(String.valueOf(Integer.parseInt(time.getText()) + 1));
    }));
    Timeline flowDown = new Timeline(new KeyFrame(Duration.millis(16.63), event -> {
        checkBalls();
//        addThings();
        try {
            changeLabels();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        checkFinish();
        showScore();
    }));
    public void showScore(){
        score = hitBalls - Integer.parseInt(time.getText()) / 10;
        scoreLabel.setText(String.valueOf(score));
    }
    public void checkBalls(){
        for(NormalBall normalBall : shownBallsArray)
            group.getChildren().remove(normalBall);
        if(board.isShowArrow()) {
            shownBallsArray.clear();
            double difX = Math.abs(board.getX() - mouseX);
            double difY = Math.abs(board.getY() - mouseY);
            double qotr = Math.sqrt(difY * difY + difX * difX);
            if(qotr <= STANDARDDISS)
                return;
            showBalls(board.getX(), board.getY(), mouseX , mouseY , DISTANCE,
                    mouseX < board.getX() ? -1 : 1);
        }
    }

    public boolean checkBallsTouch(double startX, double startY, NormalBall normalBall){
        for(Pair pair : board.getShapes()){
            Object o = pair.getKey();
            if(o instanceof Block)
                if(((Block)o).touch(normalBall))
                    return true;
            if(o instanceof Circle)
                if(((Circle)o).touch(normalBall))
                    return true;
        }
//        System.out.println("\n" + startX + " " + startY);
        if(startX <= 0 || startX >= 600 - board.getNORMALBALLRAD() * 2 || startY <= UPPAGE + board.getNORMALBALLRAD() || startY >= DOWNPAGE - board.getNORMALBALLRAD())
            return true;
        return false;
    }
    ArrayList<NormalBall> shownBallsArray = new ArrayList<>();
    public void showBalls(double startX, double startY, double finalX, double finalY, double difference, double xChange){
        NormalBall normalBall = new NormalBall(startX, startY, board.getNORMALBALLRAD(), Board.getBallColor());
        if(finalY > DOWNPAGE || finalY < UPPAGE)
            return;
//        if(Math.sqrt((startX - finalX) * (startX - finalX) + (finalY - startY) * (finalY - startX)) <= Math.abs(board.getNORMALBALLRAD() - ))
        if(startX != board.getX() || startY != board.getY()) {
            if(checkBallsTouch(startX, startY, normalBall))
                return;

            group.getChildren().add(normalBall);
        }
        shownBallsArray.add(normalBall);
        normalBall.setOpacity(SHADOWINT);

        double difX = Math.abs(startX - finalX);
        double difY = Math.abs(startY - finalY);
        double qotr = Math.sqrt(difY * difY + difX * difX);
        double difWantedX = difX * difference / qotr;
        double difWantedY = difY * difference / qotr;
        double nextX = startX + difWantedX * xChange;
        double nextY = startY - difWantedY;
        if(difWantedY / difWantedX <= 0.15)
            return;
        showBalls(nextX, nextY, finalX, finalY, difference, xChange);
    }

    public void mousePressed(double x, double y){
        if(y > DOWNPAGE - STANDARDDISS || y < UPPAGE || x < 0 || x > 600)return;
        if(isRunning)return;
        flowDown.pause();
        shootBall();
    }

    private int danceTimelineNum = 0;
    Timeline speedTimeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> {
            ballGo = 1;
    }));
    Timeline powerTimeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> {
            decreaseBall = 1;
    }));
    //TODo : sargije : function and uses of this, maybe double x , y and use them, and change them in mouseMoves function

    HashMap<Node, Color> beforeDance = new HashMap<>();
    Timeline danceTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
        danceTimelineNum ++;
        for(Pair pair : board.getShapes()){
            Node node = (Node) pair.getKey();
            double rand = Math.random() * 100;

            if(rand < 40)
                node.setOpacity(0);
            else node.setOpacity(1);
            if(node instanceof NormalBlock){
                double rand1 = Math.random();
                double rand2 = Math.random();
                double rand3 = Math.random();
                ((NormalBlock)node).setColor(new Color(rand1, rand2, rand3, 1));
            }

        }
        double rand = Math.random() * 100;
        if(rand < 20)
            group.setOpacity(0);
        else group.setOpacity(1);
        rand = Math.random() * 100;
        if(rand < 50) {
            mainBall.setOpacity(0);
        }
        else {
            mainBall.setOpacity(1);
        }
        for(Object o : group.getChildren()){
            if(o instanceof NormalBall){
                rand = Math.random() * 100;
                if(rand < 40)
                    ((NormalBall)o).setOpacity(0);
                else ((NormalBall)o).setOpacity(1);
            }
        }
        if(danceTimelineNum  == 10000 / 100){
            danceTimelineNum = 0;
            ArrayList<Pair<Node, Boolean>> shapes = board.getShapes();
            for(Pair pair : shapes){
                Node node = (Node) pair.getKey();
                node.setOpacity(1);
                if(node instanceof NormalBlock){
//                    Color color = beforeDance.get(node);
                    Color color = board.getNewColor();
                    ((NormalBlock) node).setBackground(Background.fill(color));
                }
            }
            group.setOpacity(1);
            beforeDance.clear();
            mainBall.setOpacity(1);
            for(Object o : group.getChildren()){
                if(o instanceof NormalBall){
                    ((NormalBall)o).setOpacity(1);
                }
            }
        }
        for(NormalBall normalBall : shownBallsArray)
            normalBall.setOpacity(SHADOWINT);
    }));
    public void dance(){

        //TODO
        ArrayList<Pair<Node, Boolean>> shapes = board.getShapes();
        for(Pair pair : shapes){
            Node node = (Node) pair.getKey();
            Color color = Color.GOLDENROD;
            if(node instanceof NormalBlock normalBlock) {
                //TODO check for null color
                double red = normalBlock.getRed();
                double green = normalBlock.getGreen();
                double blue = normalBlock.getBlue();
                color = new Color(red, green, blue, 1);
            }
            beforeDance.put(node, color);
        }
        danceTimeline.setCycleCount(10000 / 100);
        danceTimelineNum = 0;
        danceTimeline.stop();
        danceTimeline.play();
    }
    public void power(){
//        System.out.println("run");
        powerTimeline.stop();
        powerTimeline.setCycleCount(1);
        decreaseBall = 2;
        powerTimeline.play();
//        System.out.println(" " + decreaseBall);
    }
    public void speed(){
        speedTimeline.stop();
        speedTimeline.setCycleCount(2);
        ballGo = 2;
//        ballRate = defaultBallRate * 2;
        speedTimeline.play();
    }

    ArrayList<Pair<ImageView, Long>> views = new ArrayList<>();
    public void removeBombExplosion(){
        ArrayList<Pair<ImageView, Long>> removes = new ArrayList<>();
        for(Pair pair : views){
            ImageView imageView = (ImageView) pair.getKey();
            long l = (long) pair.getValue();
            if(System.currentTimeMillis() - l >= 3000){
                removes.add(pair);
                group.getChildren().remove(imageView);
            }
        }
        for(Pair pair : removes)
            views.remove(pair);
        removes.clear();
    }
    Image bombImage;
    public void showBombExplosion(BombBlock block){
        soundLoader.playExplosion();
        ImageView imageView = new ImageView(bombImage);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setTranslateX(block.getTranslateX());
        imageView.setTranslateY(block.getTranslateY() - 30);
        group.getChildren().add(imageView);
        views.add(new Pair<>(imageView, System.currentTimeMillis()));

    }
    ArrayList<Pair<Node, Boolean>> removes = new ArrayList<>();
    public void bombExplosion(BombBlock bombBall){
        //TODo : complete this
//        System.out.println(bombBall);
        ArrayList<BombBlock> nextBlocks = new ArrayList<>();
        double startX = bombBall.getCenterX();
        double startY = bombBall.getCenterY();

        showBombExplosion(bombBall);

        ArrayList<Pair<Node, Boolean>> shape = board.getShapes();
        for(Pair pair : shape){
            Node node = (Node) pair.getKey();
            if(node.getTranslateY() == bombBall.getTranslateY() && node.getTranslateX() == bombBall.getTranslateX())
                continue;
            if(node instanceof Block){
                double centerX = node.getTranslateX() + board.getWIDTH() / 2;
                double centerY = node.getTranslateY() + board.getHEIGHT() / 2;
                if(((Block)node).distance(centerX, centerY) <= BOMBRATE){
                    int num = Integer.parseInt(((Block) node).getText());
                    num = Math.max(num - 50, 0);
                    ((Block) node).setText(String.valueOf(num));
                    if(num == 0)
                        removes.add(new Pair<>(node,(Boolean) pair.getValue()));
                }
            }
        }
        for(Pair pair : removes){
            Node node = (Node) pair.getKey();
//            System.out.println(node);
            board.getShapes().remove(pair);
            group.getChildren().remove(node);
            if(node instanceof DanceBlock)
                dance();
            else if(node instanceof BombBlock)
                nextBlocks.add((BombBlock) node);
        }
        removes.clear();
        for(BombBlock block : nextBlocks)
            bombExplosion(block);
        nextBlocks.clear();
    }
    private int newBalls = 0, decreaseBall = 1;
    private boolean isRunning = false, isTouchFloor = false, pastShowArrow = false;
    private double nowX, nowY, changeX, changeY;
    private int remainBallBeforeShoot = 1, differentTime = 0;
    private NormalBall shadowBall;
    private ArrayList<NormalBall> balls;
    ArrayList<Node> removeBalls = new ArrayList<>();
    Timeline shoot = new Timeline(new KeyFrame(Duration.millis(16), event -> {
        differentTime ++;
        if(remainBalls.getText().equals("0") && group.getChildren().contains(mainBall))
            group.getChildren().remove(mainBall);
        if(Integer.parseInt(remainBalls.getText()) > 0 && differentTime == 2){
            remainBalls.setText(String.valueOf(Integer.parseInt(remainBalls.getText()) - 1));
            NormalBall normalBall = new NormalBall(board.getX(), board.getY(), board.getNORMALBALLRAD(), Board.ballColor);

            normalBall.setChangeY(changeY);
            normalBall.setChangeX(changeX);
            balls.add(normalBall);
            group.getChildren().add(normalBall);
            differentTime = 0;
        }

        removeBombExplosion();
        showScore();

        ArrayList<Pair<Node, Boolean>> removes = new ArrayList<>();
        for(NormalBall normalBall : balls){
//            System.out.println(normalBall.getTranslateX() + " " + normalBall.getTranslateY() + " " +
//                    normalBall.getBallCenterX() + " " + normalBall.getBallCenterY());
            if(normalBall.getBallCenterY() >= DOWNPAGE - board.getNORMALBALLRAD() + VAR) {
                if (!isTouchFloor) {
                    isTouchFloor = true;
                    nowX = normalBall.getX();
                    nowY = 700 - board.getNORMALBALLRAD() * 2;
                    shadowBall = new NormalBall(nowX, nowY, board.getNORMALBALLRAD(), Color.LIME);
                    shadowBall.setOpacity(SHADOWINT);
                    group.getChildren().add(shadowBall);

                    // TODO maybe here for change the y of the Balls

                }
                group.getChildren().remove(normalBall);
                removeBalls.add(normalBall);
            }
            double nextX = normalBall.getX() + normalBall.getChangeX() * ballGo;
            double nextY = normalBall.getY() + normalBall.getChangeY() * ballGo;
//            System.out.println(ballGo);
//            nextX *= ballGo;
//            nextY *= ballGo;
            normalBall.set(nextX, nextY);

            if(nextY <= UPPAGE + VAR + 5)
                normalBall.setChangeY(normalBall.getChangeY() * -1);
//            if(nextY + normalBall.getRadius() * 2 >= DOWNPAGE - V)
            if(nextX + normalBall.getRadius() * 2 >= 600 + VAR + 4)
                normalBall.setChangeX(normalBall.getChangeX() * -1);
            if(nextX <= VAR + 5)
                normalBall.setChangeX(normalBall.getChangeX() * -1);

            ArrayList<Pair<Node, Boolean>> shapes = board.getShapes();





            System.out.println(shapes.size());
            //TODO : check
//
//
//
//
//
//            sadsd
            for(Pair pair : shapes){
                Node node = (Node) pair.getKey();
                boolean shown = (boolean) pair.getValue();
//                if(shown)
                {
                    if(node instanceof Circle && ((Circle)node).touch(normalBall)) {
                        if (node instanceof SpeedBall){
                            speed();
                        }
                        else if(node instanceof PowerBall){
                            power();
                        }
                        else if(node instanceof AdditionalBall){
                            newBalls ++;
                        }
                        else if(node instanceof HeartBall){
                            hearts ++;
                        }
                        removes.add(pair);
                    }
                    else if(node instanceof Block && ((Block)node).touch(normalBall)){
//                        System.out.println("dasdas");
                        //TODO : check touch and change the changeX and changeY
//                        System.out.println(decreaseBall);
                        ((Block)node).setText(String.valueOf(Integer.parseInt(((Block)node).getText()) - decreaseBall));
                        if(((Block)node).getText().equals("0") || ((Block)node).getText().equals("-1")){
                            removes.add(new Pair<>(node, shown));
                            if(node instanceof DanceBlock)
                                dance();
                            else if(node instanceof BombBlock)
                                bombExplosion((BombBlock) node);
                        }

                        //TODO : check the gooshe point
                        // TODo : 2 touches when it touch gooshe of balls sometime, we shouldn't change the x = y or x = -y for some
                        // TODO : touch, we maybe should check the change x, change y, last point of the ball
                        boolean touchLeft = ((Block)node).touchLeft(normalBall);
                        boolean touchRight = ((Block)node).touchRight(normalBall);
                        boolean touchDown = ((Block)node).touchDown(normalBall);
                        boolean touchUp = ((Block)node).touchUp(normalBall);

                        if(touchLeft || touchRight)
                            normalBall.setChangeX(normalBall.getChangeX() * -1);
                        if(touchDown || touchUp)
                            normalBall.setChangeY(normalBall.getChangeY() * -1);
                        if(!touchDown && !touchLeft && !touchRight && !touchUp){
                            //   / line : x = y, y = x;
                            //   \ line : x = y * -1, y = x * -1
                            if(normalBall.getBallCenterX() < node.getTranslateX() + board.getWIDTH() / 2 &&
                                    normalBall.getBallCenterY() < node.getTranslateY() + board.getHEIGHT() / 2){
                                double tempX = normalBall.getChangeX() * -1, tempY = normalBall.getChangeY() * -1;
                                normalBall.setChangeX(tempY);
                                normalBall.setChangeY(tempX);
                            }
                            else if(normalBall.getBallCenterX() < node.getTranslateX() + board.getWIDTH() / 2 &&
                                    normalBall.getBallCenterY() > node.getTranslateY() + board.getHEIGHT() / 2){
                                double tempX = normalBall.getChangeX(), tempY = normalBall.getChangeY();
                                normalBall.setChangeX(tempY);
                                normalBall.setChangeY(tempX);
                            }
                            else if(normalBall.getBallCenterX() > node.getTranslateX() + board.getWIDTH() / 2 &&
                                    normalBall.getBallCenterY() > node.getTranslateY() + board.getHEIGHT() / 2){
                                double tempX = normalBall.getChangeX() * -1, tempY = normalBall.getChangeY() * -1;
                                normalBall.setChangeX(tempY);
                                normalBall.setChangeY(tempX);
                            }
                            else if(normalBall.getBallCenterX() > node.getTranslateX() + board.getWIDTH() / 2 &&
                                    normalBall.getBallCenterY() < node.getTranslateY() + board.getHEIGHT() / 2){
                                double tempX = normalBall.getChangeX(), tempY = normalBall.getChangeY();
                                normalBall.setChangeX(tempY);
                                normalBall.setChangeY(tempX);
                            }
                        }
                    }
                }
            }

        }
        for(Pair pair : removes) {
            Node node = (Node) pair.getKey();
            group.getChildren().remove(node);
            board.getShapes().remove(pair);
            if(node instanceof Block)
                hitBalls += ((Block)node).getFirstNum();
        }
        removes.clear();
//
        for(Object o : removeBalls)
            balls.remove(o);
        removeBalls.clear();



        if(balls.isEmpty())
            isRunning = false;
        if(!isRunning) {
            finishShoot();
        }
    }));
    public void finishShoot(){
        board.setShowArrow(pastShowArrow);
        checkBalls();
        if(!isTouchFloor){
            board.newBoard();
            nowX = board.getX();
            nowY = board.getY();
        }

        group.getChildren().remove(shadowBall);
        remainBallBeforeShoot ++;
        remainBalls.setText(String.valueOf(remainBallBeforeShoot + newBalls));
        shoot.stop();
//        System.out.println(nowX + " " + nowY);
        board.setX(nowX);
        board.setY(nowY);

        double nowXMainBall = shadowBall == null ? nowX : shadowBall.getX();
        double nowYMainBall = shadowBall == null ? nowY : shadowBall.getY();

        mainBall = new NormalBall(nowXMainBall, nowYMainBall, board.getNORMALBALLRAD(), Board.getBallColor());
        group.getChildren().add(mainBall);
        for(Pair pair : board.getShapes()){
            Node node = (Node) pair.getKey();
            checkToAdd += (int) (board.getHEIGHT() / (downRate * 2));
            node.setTranslateY(node.getTranslateY() + (board.getHEIGHT() / (downRate * 2)));
        }
        flowDown.play();
    }
    public void shootBall(){
//        System.out.println("now");

        double startX = board.getX(), startY = board.getY(),
                finalX = mouseX, finalY = mouseY, difference = defaultBallRate;
        double difX = Math.abs(startX - finalX);
        double difY = Math.abs(startY - finalY);
        double qotr = Math.sqrt(difY * difY + difX * difX);
        double difWantedX = difX * difference / qotr;
        double difWantedY = difY * difference / qotr;
        double nextX = difWantedX * (finalX < startX ? -1 : 1);
        double nextY = difWantedY * -1;

        //startX <= 0 || startX >= 600 - board.getNORMALBALLRAD() * 2 || startY <= UPPAGE + board.getNORMALBALLRAD() || startY >= DOWNPAGE - board.getNORMALBALLRAD()
        if(finalY <= UPPAGE + board.getNORMALBALLRAD() || finalY >= DOWNPAGE - board.getNORMALBALLRAD() * 2 + VAR ||
            difWantedY / difWantedX <= 0.15) {
            flowDown.play();
            return;
        }

        differentTime = 1;
//        decreaseBall = 1;
        newBalls = 0;
        isTouchFloor = false;
        isRunning = true;
        ballRate = defaultBallRate;
//        ballGo = 1;
        pastShowArrow = board.isShowArrow();
        board.setShowArrow(false);
        checkBalls();

        changeX = nextX;
        changeY = nextY;
//        System.out.println(
//                startX + " " + startY + " " + finalX + " " + finalY + " "
//                        + difference + " " + nextX + " " + nextY
//        );
        remainBallBeforeShoot = Integer.parseInt(remainBalls.getText());
        balls.clear();
        isRunning = true;
        isTouchFloor = false;
        shoot.setCycleCount(Animation.INDEFINITE);

        shoot.play();
    }

    String name, point;

    private void checkFinish() {
        ArrayList<Pair<Node, Boolean>> removes = new ArrayList<>();
        for(Pair pair : board.getShapes()){
            Node node = (Node) pair.getKey();
            boolean shown = (boolean) pair.getValue();
            if(node instanceof Circle)
                if(node.getTranslateY() + ((Circle)node).getRad() * 2 >= DOWNPAGE - board.getNORMALBALLRAD() * 2){
                    removes.add(pair);
                    group.getChildren().remove(node);
                }
            if(node instanceof Block)
                if(((Block)node).getTranslateY() + board.getHEIGHT() >= DOWNPAGE - board.getNORMALBALLRAD() * 2){
                    flowDown.stop();
                    danceTimeline.stop();
                    speedTimeline.stop();
                    new Lose(name, String.valueOf(score), GraphicAgent.difficulty, board.isShowArrow());
                    return;
                }
        }
    }

    public void changeLabels() throws MalformedURLException {
        //TODo maybe hhere

        removeBombExplosion();

        checkToAdd ++;
        if(checkToAdd >= board.getHEIGHT() / downRate) {
            board.addNewLayer();
            checkToAdd = 0;
        }
        ArrayList<Pair<Node, Boolean>> shapes = board.getShapes();
        for(Pair pair : shapes){
            Node node = (Node) pair.getKey();
            Boolean shown = (Boolean) pair.getValue();
            if(!group.getChildren().contains(node)) {
                group.getChildren().add(node);
                group.getChildren().remove(line1);
                group.getChildren().add(line1);
                group.getChildren().remove(line2);
                group.getChildren().add(line2);
            }
            if(node.getTranslateY() >= UPPAGE - board.getHEIGHT())
                shown = true;

            if(shown)
                node.setEffect(null);
            else node.setEffect(new BoxBlur());

            node.setTranslateY(node.getTranslateY() + downRate);
            if(node instanceof Circle)
                ((Circle)node).setCenterY(((Circle)node).getCenterY() + downRate);
        }
    }

    public static void setDifficulty(String difficulty) {
        GraphicAgent.difficulty = difficulty;
    }

    public GraphicAgent(SoundLoader soundLoader, String difficulty, String name, boolean showArrows) {
        this.soundLoader = soundLoader;
        group = new Group();
        ImageView imageView = new ImageView(new Image(new File("src/main/resources/game/bricksbreakerplus/images/Background.jpg").toURI().toString()));
        imageView.setFitWidth(650);
        imageView.setFitHeight(850);
        group.getChildren().add(imageView);
        scene = new Scene(group, 600, 800);
        board = new Board(difficulty, name, showArrows);
        balls = new ArrayList<>();

        GraphicAgent.setDifficulty(difficulty);
        if(difficulty.equals("normal"))
            downRate = 0.55;
        if(difficulty.equals("hard"))
            downRate = 0.65;

        mainBall = new NormalBall(board.getX(), board.getY(), board.getNORMALBALLRAD(), Board.getBallColor());
        group.getChildren().add(mainBall);

        time = new Label("0");
        time.setPrefWidth(70);
//        time.setPrefHeight(50);
        time.setFont(new Font("Bold", 18));
        time.setTranslateX(550 - time.getPrefWidth() / 2);
        time.setTranslateY(720);
        time.setAlignment(Pos.CENTER);
        group.getChildren().add(time);

        scoreLabel = new Label("0");
        scoreLabel.setFont(new Font("Bold", 18));
        scoreLabel.setPrefWidth(100);
        scoreLabel.setTranslateX(150 - scoreLabel.getPrefWidth() / 2);
        scoreLabel.setTranslateY(745);
        scoreLabel.setAlignment(Pos.CENTER);
        group.getChildren().add(scoreLabel);

        nameLabel = new Label(name);
        nameLabel.setFont(new Font("Bold", 18));
        nameLabel.setPrefWidth(100);
        nameLabel.setTranslateX(150 - scoreLabel.getPrefWidth() / 2);
        nameLabel.setTranslateY(715);
        nameLabel.setAlignment(Pos.CENTER);
        group.getChildren().add(nameLabel);

        timeString = new Label("Time:");
//        timeString.setPrefHeight(50);
        timeString.setFont(new Font("Bold", 18));
        timeString.setTranslateX(450);
        timeString.setTranslateY(720);
        timeString.setAlignment(Pos.CENTER);
        group.getChildren().add(timeString);

        bombImage = new Image(new File("src/main/resources/game/bricksbreakerplus/images/Explosion.png").toURI().toString());

        remainBalls = new Label("1");
//        remainBalls.setPrefHeight(50);
        remainBalls.setPrefWidth(100);
        remainBalls.setTranslateX(300 - remainBalls.getPrefWidth() / 2);
        remainBalls.setTranslateY(700);
        remainBalls.setAlignment(Pos.CENTER);
        remainBalls.setFont(new Font("Bold", 18));
        group.getChildren().add(remainBalls);

        flowDown.setCycleCount(Animation.INDEFINITE);
        changeTime.setCycleCount(Animation.INDEFINITE);

        mouseY = board.getY();
        mouseX = board.getX();

        scene.setOnMouseMoved(event -> {
//            System.out.println("sd");
//            double difX = Math.abs(board.getX() - event.getX());
//            double difY = Math.abs(board.getY() - event.getY());
//            double qotr = Math.sqrt(difY * difY + difX * difX);
//            System.out.println(qotr + " " + difX + " " + difY);
            mouseX = event.getX();
            mouseY = event.getY();
            checkBalls();
        });
        scene.setOnMousePressed(event -> mousePressed(event.getX(), event.getY()));
    }
    Line line1, line2;
    public void run(){
        line1 = new Line(0, UPPAGE, 600, UPPAGE);
        line2 = new Line(0, DOWNPAGE - board.getNORMALBALLRAD() * 2, 600, DOWNPAGE - board.getNORMALBALLRAD() * 2);

        group.getChildren().add(line1);
        group.getChildren().add(line2);
        primaryStage.setScene(scene);
        flowDown.play();
        changeTime.play();
    }
}

// TODo : check the finish point, additional heart, ...
//TODO : check ball shadow in board
//todo : check if the name is not empty in new game scene