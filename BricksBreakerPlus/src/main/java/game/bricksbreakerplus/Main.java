package game.bricksbreakerplus;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        new BricksBreakerPlus(primaryStage);
    }

}
