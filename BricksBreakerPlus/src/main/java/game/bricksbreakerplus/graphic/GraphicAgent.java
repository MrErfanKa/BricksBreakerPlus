package game.bricksbreakerplus.graphic;

import game.bricksbreakerplus.Board;
import game.bricksbreakerplus.collisions.balls.Circle;
import game.bricksbreakerplus.collisions.balls.NormalBall;
import game.bricksbreakerplus.collisions.blocks.Block;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.shape.Line;
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
    private final double DISTANCE = 35, SHADOWINT = 0.3, STANDARDDISS = 20;
    private Board board;
    private final double UPPAGE = 100, DOWNPAGE = 700;
    public void switchScene(Scene scene){
        primaryStage.setScene(scene);
    }


    private Label time;
    Timeline changeTime = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        time.setText(String.valueOf(Integer.parseInt(time.getText()) + 1));
    }));
    private double downRate = 0.5;
    private int checkToAdd = 0;

    private double mouseX, mouseY;

    public void checkBalls(){
//        System.out.println(shownBallsArray.size());
//        System.out.println(time.getText());
//        board touch = false;
//        for(NormalBall normalBall : shownBallsArray)
//            if(checkBallsTouch(normalBall.getX(), normalBall.getY(), normalBall))
//                touch = true;
//        if(touch)
//


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
        if(startX <= 0 || startX >= 600 - board.getRAD() || startY <= UPPAGE + board.getRAD() || startY >= DOWNPAGE - board.getRAD())
            return true;
        return false;
    }
    ArrayList<NormalBall> shownBallsArray = new ArrayList<>();
    public void showBalls(double startX, double startY, double finalX, double finalY, double difference, double xChange){
        NormalBall normalBall = new NormalBall(startX, startY, board.getNORMALBALLRAD(), Board.getBallColor());

//        if(Math.sqrt((startX - finalX) * (startX - finalX) + (finalY - startY) * (finalY - startX)) <= Math.abs(board.getNORMALBALLRAD() - ))
        shownBallsArray.add(normalBall);
        normalBall.setOpacity(SHADOWINT);
//        if(startX != board.getX() || startY != board.getY()) {
            group.getChildren().add(normalBall);
            if(checkBallsTouch(startX, startY, normalBall))
                return;
//        }

        double difX = Math.abs(startX - finalX);
        double difY = Math.abs(startY - finalY);
//        if(difY < 20)difY = 20;

        double qotr = Math.sqrt(difY * difY + difX * difX);
        double difWantedX = difX * difference / qotr;
        double difWantedY = difY * difference / qotr;
        double nextX = startX + difWantedX * xChange;
        double nextY = startY - difWantedY;
        if(difWantedY / difWantedX <= 0.25)
            return;
        showBalls(nextX, nextY, finalX, finalY, difference, xChange);
    }

    public void mousePressed(double x, double y){
        if(y > 500 || y < 100)return;
        flowDown.pause();
        shootBall();
        flowDown.play();
    }

    public void shootBall(){
        // TODO
    }

    Timeline flowDown = new Timeline(new KeyFrame(Duration.millis(16.63), event -> {
        checkBalls();
//        addThings();
        try {
            changelabes();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        changeArrow();
    }));

    private void changeArrow() {
        if(!board.isShowArrow())
            return;


        // TODO
    }

    public void changelabes() throws MalformedURLException {
        checkToAdd ++;
        if(checkToAdd == board.getHEIGHT())
            board.addNewLayer();
        ArrayList<Pair<Node, Boolean>> shapes = board.getShapes();
        for(Pair pair : shapes){
            Node node = (Node) pair.getKey();
            Boolean shown = (Boolean) pair.getValue();
            if(node.getTranslateY() >= UPPAGE)
                shown = true;

            if(shown)
                node.setEffect(null);
            else node.setEffect(new BoxBlur());

            node.setTranslateY(node.getTranslateY() + downRate);
        }
    }

    public static void setDifficulty(String difficulty) {
        GraphicAgent.difficulty = difficulty;
    }

    public GraphicAgent() {
        group = new Group();
        scene = new Scene(group, 600, 800);
        board = new Board(difficulty);
        time = new Label("0");
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
        scene.setOnMouseClicked(event -> mousePressed(event.getX(), event.getY()));
    }

    public void run(){
        Line line1 = new Line(0, UPPAGE, 600, UPPAGE);
        Line line2 = new Line(0, DOWNPAGE, 600, DOWNPAGE);
        group.getChildren().add(line1);
        group.getChildren().add(line2);
        primaryStage.setScene(scene);
//        flowDown.play();
//        changeTime.play();
    }
}
