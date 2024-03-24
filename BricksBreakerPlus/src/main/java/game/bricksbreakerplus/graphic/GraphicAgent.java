package game.bricksbreakerplus.graphic;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicAgent {
    public static Stage primaryStage;
    public void switchScene(Scene scene){
        primaryStage.setScene(scene);
    }
}
