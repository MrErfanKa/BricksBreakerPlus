module game.bricksbreakerplus {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens game.bricksbreakerplus.controls to javafx.fxml;
    exports game.bricksbreakerplus;
}