package game.bricksbreakerplus.graphic;

import game.bricksbreakerplus.saveAndLoad.Saver;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;

public class History {
    private ScrollPane scrollPane;
    private Scene scene;
    private Group hBox;
    private Button button;
    public History(){
        scrollPane = new ScrollPane();
        scene = new Scene(scrollPane, 600, 800);
        GraphicAgent.switchScene(scene);
        hBox = new Group();
//        hBox.setTranslateX(100);
//        hBox.setTranslateY(200);
//        hBox.setPrefWidth(400);
        scrollPane.setContent(hBox);

        ImageView imageView = new ImageView(new Image(new File("src/main/resources/game/bricksbreakerplus/images/Background.jpg").toURI().toString()));
        imageView.setFitWidth(650);
        imageView.setFitHeight(850);
        hBox.getChildren().add(imageView);

        button = new Button("Back");
//        button.setTranslateX(10);
        button.setAlignment(Pos.CENTER);
        button.setFont(new Font("Bold", 18));
        button.setPrefWidth(70);
        button.setPrefHeight(30);
        button.setTranslateX(40);
        button.setTranslateY(40);
        button.setOnAction(event -> {
            GraphicAgent.switchScene(SceneLoader.getStart());
        });
        hBox.getChildren().add(button);

        update();

//        ScrollPane root = new ScrollPane();
//        Scene scene = new Scene(root, 300, 250);
//        Text text = new Text("The look and feel of JavaFX applications "
//                + "can be customized. Cascading Style Sheets (CSS) separate "
//                + "appearance and style from implementation so that developers can "
//                + "concentrate on coding. Graphic designers can easily "
//                + "customize the appearance and style of the application "
//                + "through the CSS. If you have a web design background,"
//                + " or if you would like to separate the user interface (UI) "
//                + "and the back-end logic, then you can develop the presentation"
//                + " aspects of the UI in the FXML scripting language and use Java "
//                + "code for the application logic. If you prefer to design UIs "
//                + "without writing code, then use JavaFX Scene Builder. As you design the UI, "
//                + "Scene Builder creates FXML markup that can be ported to an Integrated Development "
//                + "Environment (IDE) so that developers can add the business logic.");
//        text.wrappingWidthProperty().bind(scene.widthProperty());
//        root.setFitToWidth(true);
//        root.setContent(text);
//        GraphicAgent.switchScene(scene);

    }
    double y = 0;
    public void update(){
        for(Pair pair : Saver.games){
            String name = (String) ((Pair)pair.getKey()).getKey();
            String time = (String) ((Pair)pair.getKey()).getValue();
            int score = ((int) ((Pair)pair.getValue()).getKey());
            String dif = (String) ((Pair)pair.getValue()).getValue();

            Label n = new Label(name);
            n.setTranslateX(150);
            n.setTranslateY(10 + y);
            n.setPrefWidth(100);
            n.setPrefHeight(30);

            Label t = new Label(time);
            t.setTranslateX(250);
            t.setTranslateY(10 + y);
            t.setPrefWidth(200);
            t.setPrefHeight(30);

            Label s = new Label(String.valueOf(score));
            s.setTranslateX(450);
            s.setTranslateY(10 + y);
            s.setPrefWidth(50);
            s.setPrefHeight(30);

            Label d = new Label(dif);
            d.setTranslateX(500);
            d.setTranslateY(10 + y);
            d.setPrefWidth(50);
            d.setPrefHeight(30);

            hBox.getChildren().add(n);
            hBox.getChildren().add(t);
            hBox.getChildren().add(s);
            hBox.getChildren().add(d);
            y += 30;
        }
    }
}
