module fx.games.ninjafrog {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens fx.games.ninjafrog to javafx.fxml;
    exports fx.games.ninjafrog;
}