module game.bricksbreakerplus {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires org.json;

    opens game.bricksbreakerplus.controls to javafx.fxml;
    exports game.bricksbreakerplus;
}