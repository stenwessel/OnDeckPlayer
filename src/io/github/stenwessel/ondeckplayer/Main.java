package io.github.stenwessel.ondeckplayer;

import io.github.stenwessel.ondeckplayer.player.Player;
import io.github.stenwessel.ondeckplayer.player.PlayerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;

public class Main extends Application {

    private final Player player = new Player();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        loader.setResources(ResourceBundle.getBundle("font.fontawesome"));

        loader.setControllerFactory((controllerClass) -> {
            if (controllerClass == PlayerController.class) {
                return new PlayerController(this);
            } else if (controllerClass == Controller.class) {
                return new Controller(this);
            }
            try {
                return controllerClass.newInstance();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();
        primaryStage.setTitle("OnDeckPlayer");
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("css/main.css");

        primaryStage.setScene(scene);
        primaryStage.getIcons().addAll(getIcons());
        primaryStage.show();
    }

    public Player getPlayer() {
        return player;
    }

    private Collection<Image> getIcons() {
        return new HashSet<Image>(){{
            Collections.addAll(this,
                    new Image("img/icon/icon16.png"),
                    new Image("img/icon/icon32.png"),
                    new Image("img/icon/icon48.png"),
                    new Image("img/icon/icon128.png"),
                    new Image("img/icon/icon256.png")
            );
        }};
    }
}
