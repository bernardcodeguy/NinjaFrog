package fx.games.ninjafrog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Parent root = loader.load();

        // Set the controller for the FXML file
        InGameController controller = loader.getController();
        Scene scene = new Scene(root, 1280, 720);
        stage.setResizable(false);
        stage.setMaximized(false);
        scene.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.A){
                controller.shoot();
                return;
            }

            controller.keys.put(e.getCode(), true);
        });

        scene.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.A){
                return;
            }
            controller.keys.put(event.getCode(), false);

            Player.animation.stop();
        });

        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("f.png")));
        stage.setTitle("Ninja Frog");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}