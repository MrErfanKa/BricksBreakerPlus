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
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

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
    private Board board;
    private final double UPPAGE = 100, DOWNPAGE = 700;
    public void switchScene(Scene scene){
        primaryStage.setScene(scene);
    }


    private Label time, remainBalls, timeString;
    private double downRate = 0.4, addNewThings = 100 / downRate, ballRate = 3.5, defaultBallRate = 3.5;
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
        changeArrow();
    }));
    public void checkBalls(){
        if(board.isShowArrow()) {
            for(NormalBall normalBall : shownBallsArray)
                group.getChildren().remove(normalBall);
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
//        if(difWantedY / difWantedX <= 0.25)
//            System.out.println("o");
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

    private int speedTimelineNum = 0, powerTimelineNum = 0, danceTimelineNum = 0;
    Timeline speedTimeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> {
        speedTimelineNum ++;
        ballRate *= 2;
        if(speedTimelineNum == 2) {
            ballRate /= 2;
            speedTimelineNum = 0;
        }
    }));
    Timeline powerTimeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> {
        powerTimelineNum ++;
        decreaseBall = 2;
        if(powerTimelineNum == 2){
            decreaseBall = 1;
            powerTimelineNum = 0;
        }
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
    public void bombExplosion(BombBlock bombBall){
        //TODo : complete this
        double startX = bombBall.getCenterX();
        double startY = bombBall.getCenterY();

        ArrayList<Pair<Node, Boolean>> shapes = board.getShapes();
        ArrayList<BombBlock> nextBombs = new ArrayList<BombBlock>();
        ArrayList<Node> removes = new ArrayList<>();
        for(Pair pair : shapes){
            Node node = (Node) pair.getKey();
            boolean shown = (boolean) pair.getValue();


            if(node instanceof Block){
                if(node instanceof BombBlock){
                    ((BombBlock)node).setText(String.valueOf(Integer.parseInt(((BombBlock)node).getText()) - 50));
                    if(Integer.parseInt(((BombBlock)node).getText()) <= 50) {
                        removes.add(node);
                        nextBombs.add((BombBlock) node);
                    }
                }
                else {

                }
            }
        }
    }
    private int newBalls = 0, decreaseBall = 1;
    private boolean isRunning = false, isTouchFloor = false;
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
            double nextX = normalBall.getX() + normalBall.getChangeX();
            double nextY = normalBall.getY() + normalBall.getChangeY();
            normalBall.set(nextX, nextY);

            if(nextY <= UPPAGE + VAR + 5)
                normalBall.setChangeY(normalBall.getChangeY() * -1);
//            if(nextY + normalBall.getRadius() * 2 >= DOWNPAGE - V)
            if(nextX + normalBall.getRadius() * 2 >= 600 + VAR + 4)
                normalBall.setChangeX(normalBall.getChangeX() * -1);
            if(nextX <= VAR + 5)
                normalBall.setChangeX(normalBall.getChangeX() * -1);

            //TODO check touch
            ArrayList<Pair<Node, Boolean>> shapes = board.getShapes();
            for(Pair pair : shapes){
                Node node = (Node) pair.getKey();
                boolean shown = (boolean) pair.getValue();
//                if(shown)
                {
                    if(node instanceof Circle && ((Circle)node).touch(normalBall)) {
                        if (node instanceof SpeedBall){
                            speedTimeline.stop();
                            speedTimelineNum = 0;
                            speedTimeline.play();
                        }
                        else if(node instanceof PowerBall){
                            powerTimeline.stop();
                            powerTimelineNum = 0;
                            powerTimeline.play();
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
                        ((Block)node).setText(String.valueOf(Integer.parseInt(((Block)node).getText()) - 1));
                        if(((Block)node).getText().equals("0")){
                            //TODo : check for additional heart
                            removes.add(new Pair<>(node, shown));
                            if(node instanceof DanceBlock)
                                dance();
                            else if(node instanceof BombBlock)
                                bombExplosion((BombBlock) node);
                        }
                    }
                }
            }

        }
        for(Pair pair : removes) {
            Node node = (Node) pair.getKey();
            group.getChildren().remove(node);
            board.getShapes().remove(pair);
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
        if(!isTouchFloor){
            board.newBoard();
            nowX = board.getX();
            nowY = board.getY();
        }

        group.getChildren().remove(shadowBall);
        remainBallBeforeShoot ++;
        //TODO : add additional ball
        remainBalls.setText(String.valueOf(remainBallBeforeShoot + newBalls));
        shoot.stop();
//        System.out.println(nowX + " " + nowY);
        board.setX(nowX);
        board.setY(nowY);
        mainBall = new NormalBall(shadowBall.getX(), shadowBall.getY(), board.getNORMALBALLRAD(), Board.getBallColor());
        group.getChildren().add(mainBall);
        flowDown.play();
    }
    public void shootBall(){
        // TODO
        differentTime = 1;
        decreaseBall = 1;
        newBalls = 0;
        speedTimelineNum = 0;
        powerTimelineNum = 0;
        isTouchFloor = false;
        isRunning = true;
        ballRate = defaultBallRate;
        double startX = board.getX(), startY = board.getY(),
                finalX = mouseX, finalY = mouseY, difference = ballRate;
        double difX = Math.abs(startX - finalX);
        double difY = Math.abs(startY - finalY);
        double qotr = Math.sqrt(difY * difY + difX * difX);
        double difWantedX = difX * difference / qotr;
        double difWantedY = difY * difference / qotr;
        double nextX = difWantedX * (finalX < startX ? -1 : 1);
        double nextY = difWantedY * -1;

        //TODO : check for bad moves like sin <= 0.25

        if(finalY <= UPPAGE || finalY >= DOWNPAGE - board.getNORMALBALLRAD() * 2 - VAR ||
            difY / difX <= 0.15) {
            remainBallBeforeShoot --;
            finishShoot();
            return;
        }
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



    private void changeArrow() {
        if(!board.isShowArrow())
            return;


        // TODO
    }

    public void changeLabels() throws MalformedURLException {
        //TODo maybe hhere
        checkToAdd ++;
        if(checkToAdd == board.getHEIGHT() / downRate) {
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

    public GraphicAgent(SoundLoader soundLoader) {
        this.soundLoader = soundLoader;
        group = new Group();
        scene = new Scene(group, 600, 800);
        board = new Board(difficulty);
        balls = new ArrayList<>();

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

        timeString = new Label("Time:");
//        timeString.setPrefHeight(50);
        timeString.setFont(new Font("Bold", 18));
        timeString.setTranslateX(450);
        timeString.setTranslateY(720);
        timeString.setAlignment(Pos.CENTER);
        group.getChildren().add(timeString);

        remainBalls = new Label("1");
        remainBalls.setPrefHeight(50);
        remainBalls.setPrefWidth(100);
        remainBalls.setTranslateX(300 - remainBalls.getPrefWidth() / 2);
        remainBalls.setTranslateY(720);
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
        line2 = new Line(0, DOWNPAGE, 600, DOWNPAGE);

        group.getChildren().add(line1);
        group.getChildren().add(line2);
        primaryStage.setScene(scene);
        flowDown.play();
        changeTime.play();
    }
}
