package io.github.stenwessel.ondeckplayer;

import io.github.stenwessel.ondeckplayer.player.Player;
import io.github.stenwessel.ondeckplayer.player.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    @FXML public MenuBar menuBar;

    private final Player player;

    public Controller(Main main) {
        this.player = main.getPlayer();
    }

    public void loadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("MP3 Files", "mp3"));

        File selected = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        player.play(new Song(selected));
    }
}
