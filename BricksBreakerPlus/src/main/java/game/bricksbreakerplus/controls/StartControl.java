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

    int ns = 0, hs = 0, es = 0;
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
        for(Pair pair : Saver.games){
            String name =(String) ((Pair)pair.getKey()).getKey();
            String time =(String) ((Pair)pair.getKey()).getValue();
            int score =(int) ((Pair)pair.getValue()).getKey();
            String dif =(String) ((Pair)pair.getValue()).getValue();
            if(dif.equals("easy"))
                if(score > es){
                    easyName.setText(name);
                    easyScore.setText(String.valueOf(score));
                    es = score;
                }
            if(dif.equals("normal"))
                if(score > ns){
                    normalName.setText(name);
                    normalScore.setText(String.valueOf(score));
                    ns = score;
                }
            if(dif.equals("hard"))
                if(score > hs){
                    hardName.setText(name);
                    hardScore.setText(String.valueOf(score));
                    hs = score;
                }
        }
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
