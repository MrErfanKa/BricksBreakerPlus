package game.bricksbreakerplus;

import game.bricksbreakerplus.controls.Controller;
import game.bricksbreakerplus.graphic.GraphicAgent;
import game.bricksbreakerplus.graphic.SceneLoader;
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

    public BricksBreakerPlus(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        graphicAgent = new GraphicAgent();
        GraphicAgent.primaryStage = primaryStage;
        Controller.graphicAgent = graphicAgent;

        primaryStage.setTitle("Bricks Breaker +");
        primaryStage.setScene(SceneLoader.getStart());
        primaryStage.show();


    }
}
