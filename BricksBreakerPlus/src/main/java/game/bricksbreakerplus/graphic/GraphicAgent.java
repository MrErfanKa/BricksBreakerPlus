package game.bricksbreakerplus.graphic;

import game.bricksbreakerplus.Board;
import game.bricksbreakerplus.collisions.balls.Circle;
import game.bricksbreakerplus.collisions.balls.NormalBall;
import game.bricksbreakerplus.collisions.blocks.Block;
import game.bricksbreakerplus.media.SoundLoader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.NamedArg;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GraphicAgent{
    public static Stage primaryStage;
    private Group group;
    private Scene scene;
    private static String difficulty = "easy";
    private final double DISTANCE = 10, SHADOWINT = 0.3, STANDARDDISS = 20, VAR = 0.5;
    private Board board;
    private final double UPPAGE = 100, DOWNPAGE = 700;
    public void switchScene(Scene scene){
        primaryStage.setScene(scene);
    }


    private Label time, remainBalls, timeString;
    private double downRate = 0.4, addNewThings = 100 / downRate, ballRate = 3.5;
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



    private boolean isRunning = false, isTouchFloor = false;
    private double nowX, nowY, changeX, changeY;
    private int remainBallBeforeShoot = 1, differentTime = 0;
    private NormalBall shadowBall;
    private ArrayList<NormalBall> balls = new ArrayList<>();
    Timeline shoot = new Timeline(new KeyFrame(Duration.millis(16), event -> {
        differentTime ++;
        if(Integer.parseInt(remainBalls.getText()) > 0 && differentTime == 2){
            remainBalls.setText(String.valueOf(Integer.parseInt(remainBalls.getText()) - 1));
            NormalBall normalBall = new NormalBall(board.getX(), board.getY(), board.getNORMALBALLRAD(), Board.ballColor);

            normalBall.setChangeY(changeY);
            normalBall.setChangeX(changeX);
            balls.add(normalBall);
            group.getChildren().add(normalBall);
            differentTime = 0;
        }


        ArrayList<NormalBall> removeBalls = new ArrayList<>();
        for(NormalBall normalBall : balls){
//            System.out.println(normalBall.getTranslateX() + " " + normalBall.getTranslateY() + " " +
//                    normalBall.getBallCenterX() + " " + normalBall.getBallCenterY());
            if(normalBall.getBallCenterY() >= DOWNPAGE - board.getNORMALBALLRAD() + VAR)
                if(!isTouchFloor){
                    isTouchFloor = true;
                    nowX = normalBall.getCenterX();
                    nowY = normalBall.getCenterY();
                    shadowBall = new NormalBall(nowX, nowY, board.getNORMALBALLRAD(), Color.LIME);
                    shadowBall.setOpacity(SHADOWINT);
                    group.getChildren().add(shadowBall);

                    // TODO maybe here for change the y of the Balls

                }
            if(normalBall.getBallCenterY() >= DOWNPAGE - board.getNORMALBALLRAD() + VAR){
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
//            ArrayList<Pair<Node, Boolean>> shapes = board.getShapes();
//            for(Pair pair : shapes){
//                Node node = (Node) pair.getKey();
//                boolean shown = (boolean) pair.getValue();
//                if(shown){
//                    if(node instanceof Circle && ((Circle)node).touch(normalBall))
//                        if()
//                }
//            }

        }
        for(NormalBall normalBall : removeBalls)
            balls.remove(normalBall);



        if(balls.isEmpty())
            isRunning = false;
        if(!isRunning) {
            finishShoot();
        }
    }));
    public void finishShoot(){
        group.getChildren().remove(shadowBall);
        remainBallBeforeShoot ++;
        //TODO : add additional ball
        remainBalls.setText(String.valueOf(remainBallBeforeShoot));
        shoot.stop();
        flowDown.play();
    }
    public void shootBall(){
        // TODO
        differentTime = 1;
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
