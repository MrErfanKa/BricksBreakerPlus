package game.bricksbreakerplus.controls;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML
    TextArea easy;
    @FXML
    TextArea normal;
    @FXML
    TextArea hard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
