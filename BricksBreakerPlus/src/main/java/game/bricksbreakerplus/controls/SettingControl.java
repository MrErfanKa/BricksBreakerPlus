package game.bricksbreakerplus.controls;

import game.bricksbreakerplus.BricksBreakerPlus;
import game.bricksbreakerplus.Resource;
import game.bricksbreakerplus.graphic.GraphicAgent;
import game.bricksbreakerplus.graphic.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingControl extends Controller implements Initializable {

    @FXML
    Button back;
    @FXML
    RadioButton save;
    @FXML
    RadioButton doNotSave;
    @FXML
    RadioButton play;
    @FXML
    RadioButton pause;
    @FXML
    RadioButton show;
    @FXML
    RadioButton hidden;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back.setOnAction(e -> Back());
        save.setOnAction(event -> Save());
        doNotSave.setOnAction(e -> DoNotSave());
        play.setOnAction(e -> Play());
        pause.setOnAction(e -> Pause());
        show.setOnAction(e -> Show());
        hidden.setOnAction(e -> Hidden());
    }
    public void Show(){
        show.setSelected(true);
        hidden.setSelected(false);
        Resource.setShowArrows(true);
    }
    public void Hidden(){
        show.setSelected(false);
        hidden.setSelected(true);
        Resource.setShowArrows(false);
    }
    public void Play(){
        play.setSelected(true);
        pause.setSelected(false);
        Resource.setPlayMusic(true);
        BricksBreakerPlus.soundLoader.playOnRepeat();
    }
    public void Pause(){
        play.setSelected(false);
        pause.setSelected(true);
        Resource.setPlayMusic(false);
        BricksBreakerPlus.soundLoader.stop();
    }
    public void Save(){
        save.setSelected(true);
        doNotSave.setSelected(false);
        Resource.setSave(true);
    }
    public void DoNotSave(){
        save.setSelected(false);
        doNotSave.setSelected(true);
        Resource.setSave(false);
    }
    public void Back(){
        GraphicAgent.switchScene(SceneLoader.getStart());
    }
}
