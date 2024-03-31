package game.bricksbreakerplus.graphic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class SceneLoader {
    public static Scene Start, NewGame, Setting;
    static {
        try {
            Start = new Scene(new FXMLLoader(new File("./src/main/resources/game/bricksbreakerplus/fxml/Start.fxml").toURI().toURL()).load(), 600, 800);
            NewGame = new Scene(new FXMLLoader(new File("./src/main/resources/game/bricksbreakerplus/fxml/NewGame.fxml").toURI().toURL()).load(), 600, 800);
            Setting = new Scene(new FXMLLoader(new File("./src/main/resources/game/bricksbreakerplus/fxml/Setting.fxml").toURI().toURL()).load(), 600, 800);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Scene getStart() {
        try {
            Start = new Scene(new FXMLLoader(new File("./src/main/resources/game/bricksbreakerplus/fxml/Start.fxml").toURI().toURL()).load(), 600, 800);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Start;
    }

    public static Scene getNewGame() {
        return NewGame;
    }

    public static Scene getSetting() {
        return Setting;
    }
}
