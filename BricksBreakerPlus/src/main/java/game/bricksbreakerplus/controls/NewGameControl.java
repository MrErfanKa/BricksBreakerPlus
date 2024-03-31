package game.bricksbreakerplus.controls;

import game.bricksbreakerplus.Board;
import game.bricksbreakerplus.BricksBreakerPlus;
import game.bricksbreakerplus.Resource;
import game.bricksbreakerplus.graphic.GraphicAgent;
import game.bricksbreakerplus.graphic.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class NewGameControl extends Controller implements Initializable {
    @FXML
    Button start;
    @FXML
    TextField name;
    @FXML
    ColorPicker color;

    @FXML
    RadioButton easy;
    @FXML
    RadioButton normal;
    @FXML
    RadioButton hard;
    @FXML
    Button back;


    Color ballColor;
    String difficulty = "easy";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start.setOnAction(e -> Start());
        ballColor = color.getValue();
//        System.out.println(color.);
        easy.setOnAction(e -> Easy());
        normal.setOnAction(e -> Normal());
        hard.setOnAction(e -> Hard());
        back.setOnAction(e -> Back());
    }
    public void Back(){
        GraphicAgent.switchScene(SceneLoader.getStart());
    }
    public void Start(){
        ballColor = color.getValue();
        if(name.getText().equals("")){
            name.setText("-Na");
        }
        GraphicAgent graphicAgent1 = new GraphicAgent(BricksBreakerPlus.soundLoader, difficulty, name.getText(), Resource.isShowArrows());
        Board.ballColor = ballColor;
        graphicAgent1.run();
    }
    public void Hard(){
        easy.setSelected(false);
        normal.setSelected(false);
        hard.setSelected(true);
        difficulty = "hard";
    }
    public void Normal(){
        easy.setSelected(false);
        normal.setSelected(true);
        hard.setSelected(false);
        difficulty = "normal";
    }
    public void Easy(){
        easy.setSelected(true);
        normal.setSelected(false);
        hard.setSelected(false);
        difficulty = "easy";
    }
}
