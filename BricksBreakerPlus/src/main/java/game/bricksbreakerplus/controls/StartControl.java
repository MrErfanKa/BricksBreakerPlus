package game.bricksbreakerplus.controls;

import game.bricksbreakerplus.BricksBreakerPlus;
import game.bricksbreakerplus.graphic.GraphicAgent;
import game.bricksbreakerplus.graphic.History;
import game.bricksbreakerplus.graphic.SceneLoader;
import game.bricksbreakerplus.saveAndLoad.Saver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Pair;

import java.io.FileNotFoundException;
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

    @FXML
    Label easyName;
    @FXML
    Label easyScore;
    @FXML
    Label normalName;
    @FXML
    Label normalScore;
    @FXML
    Label hardName;
    @FXML
    Label hardScore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newGame.setOnAction(e -> NewGame());
        exit.setOnAction(e -> Exit());
        setting.setOnAction(e -> Setting());
        history.setOnAction(e -> History());
        try {
            Saver.load();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("sdas");
        easyScore.setText("-Na");
        easyName.setText("-Na");
        System.out.println(easyName);
        normalName.setText("-Na");
        normalScore.setText("-Na");
        hardName.setText("-Na");
        hardScore.setText("-Na");
    }
    public void History(){
        new History();
    }
    public void NewGame(){
        GraphicAgent.switchScene(SceneLoader.getNewGame());
    }
    public void Setting(){
        GraphicAgent.switchScene(SceneLoader.getSetting());
    }
    public void Exit(){
        System.exit(1);
    }
}
