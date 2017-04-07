package io.github.stenwessel.ondeckplayer;

import io.github.stenwessel.ondeckplayer.player.Player;
import io.github.stenwessel.ondeckplayer.player.PlayerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private final Player player = new Player();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        fxmlLoader.setControllerFactory((controllerClass) -> {
            if (controllerClass == PlayerController.class) {
                return new PlayerController(this);
            }
            try {
                return controllerClass.newInstance();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = fxmlLoader.load();
        primaryStage.setTitle("OnDeckPlayer");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public Player getPlayer() {
        return player;
    }
}
