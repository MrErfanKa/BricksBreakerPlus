package game.bricksbreakerplus.graphic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class SceneLoader {
    public static Scene Start, NewGame;
    static {
        try {
            Start = new Scene(new FXMLLoader(new File("./src/main/resources/game/bricksbreakerplus/fxml/Start.fxml").toURI().toURL()).load(), 600, 800);
            NewGame = new Scene(new FXMLLoader(new File("./src/main/resources/game/bricksbreakerplus/fxml/NewGame.fxml").toURI().toURL()).load(), 600, 800);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Scene getStart() {
        return Start;
    }

    public static Scene getNewGame() {
        return NewGame;
    }
}
