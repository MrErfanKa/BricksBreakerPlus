package game.bricksbreakerplus.graphic;

import game.bricksbreakerplus.BricksBreakerPlus;
import game.bricksbreakerplus.Resource;
import game.bricksbreakerplus.saveAndLoad.Saver;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;

public class Lose {
    Button start, newGame, again;
    Group group;
    Scene scene;
    public Lose(String name, String points, String difficulty, boolean showArrow) throws FileNotFoundException {
        group = new Group();
        scene = new Scene(group, 600, 800);
        GraphicAgent.switchScene(scene);

        double x = 250;

        Label label = new Label(points);
        label.setTranslateX(x);
        label.setPrefWidth(100);
        label.setTranslateY(300);
        label.setFont(new Font(18));
        label.setAlignment(Pos.CENTER);

        start = new Button("main page");
        start.setOnAction(e -> Start());
        start.setPrefWidth(120);
        start.setTranslateX(300 - 120 / 2);
        start.setTranslateY(400);
        start.setFont(new Font(18));
        start.setAlignment(Pos.CENTER);

        newGame = new Button("new game");
        newGame.setOnAction(e -> NewGame());
        newGame.setPrefWidth(110);
        newGame.setTranslateX(300 - 110 / 2);
        newGame.setTranslateY(500);
        newGame.setFont(new Font(18));
        newGame.setAlignment(Pos.CENTER);

        again = new Button("again");
        again.setOnAction(e -> Again(difficulty, name, showArrow));
        again.setPrefWidth(100);
        again.setTranslateX(x);
        again.setTranslateY(600);
        again.setFont(new Font(18));
        again.setAlignment(Pos.CENTER);

        group.getChildren().add(label);
        group.getChildren().add(start);
        group.getChildren().add(newGame);
        group.getChildren().add(again);

        System.out.println(label.getWidth());
        System.out.println(again.getWidth());
        System.out.println(newGame.getWidth());
        System.out.println(start.getWidth());
        if(Resource.isSave())
            Saver.add(name, Integer.parseInt(points), difficulty);
        Saver.save();
    }
    public void Start(){
        GraphicAgent.switchScene(SceneLoader.getStart());
    }
    public void NewGame(){
        GraphicAgent.switchScene(SceneLoader.getNewGame());
    }
    public void Again(String difficulty, String name, boolean showArrows){

        GraphicAgent graphicAgent = new GraphicAgent(BricksBreakerPlus.soundLoader, difficulty, name, showArrows);
        graphicAgent.run();
    }
}
