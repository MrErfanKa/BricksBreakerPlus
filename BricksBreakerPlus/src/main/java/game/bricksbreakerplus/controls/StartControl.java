package game.bricksbreakerplus.controls;

import game.bricksbreakerplus.graphic.GraphicAgent;
import game.bricksbreakerplus.graphic.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class StartControl extends Controller implements Initializable {
    @FXML
    Button newGame;
    @FXML
    Button history;
    @FXML
    Button setting;
    @FXML
    Button exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newGame.setOnAction(e -> NewGame());
    }
    public void NewGame(){
        GraphicAgent.switchScene(SceneLoader.getNewGame());
    }
}
