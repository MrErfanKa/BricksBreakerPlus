package game.bricksbreakerplus;

import game.bricksbreakerplus.controls.Controller;
import game.bricksbreakerplus.graphic.GraphicAgent;
import game.bricksbreakerplus.graphic.History;
import game.bricksbreakerplus.graphic.Lose;
import game.bricksbreakerplus.graphic.SceneLoader;
import game.bricksbreakerplus.media.SoundLoader;
import game.bricksbreakerplus.saveAndLoad.Saver;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class BricksBreakerPlus {
    private Stage primaryStage;
    public GraphicAgent graphicAgent;
    public static SoundLoader soundLoader;

    public BricksBreakerPlus(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        GraphicAgent.primaryStage = primaryStage;
        soundLoader = new SoundLoader();
//        graphicAgent = new GraphicAgent(soundLoader);
        Controller.graphicAgent = graphicAgent;
        soundLoader.playOnRepeat();

        Saver.load();
        primaryStage.setTitle("Bricks Breaker +");
        primaryStage.setScene(SceneLoader.getStart());
//        graphicAgent.run();
//        new Lose("dadsa", "sddad", "easy", true);
//        new History();
//        System.out.println(Saver.games);
        primaryStage.show();



    }
}
