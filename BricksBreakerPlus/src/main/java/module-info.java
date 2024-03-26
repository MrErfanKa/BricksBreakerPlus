module game.bricksbreakerplus {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;

    opens game.bricksbreakerplus.controls to javafx.fxml;
    exports game.bricksbreakerplus;
}