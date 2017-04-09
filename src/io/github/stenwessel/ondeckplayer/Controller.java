package io.github.stenwessel.ondeckplayer;

import io.github.stenwessel.ondeckplayer.player.Player;
import io.github.stenwessel.ondeckplayer.player.PlayerController;
import io.github.stenwessel.ondeckplayer.player.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    @FXML private MenuBar menuBar;
    @FXML private PlayerController playerController;

    private final Player player;

    public Controller(Main main) {
        this.player = main.getPlayer();
    }

    public void onPlayerPlayAction(ActionEvent event) {
        playerController.onPlayAction(event);
    }

    public void onAboutAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About OnDeckPlayer");
        alert.setHeaderText("About OnDeckPlayer");
        alert.setContentText("Copyright (c) 2017 Sten Wessel, MIT License.");

        alert.showAndWait();
    }

    public void loadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("MP3 Files", "mp3"));

        File selected = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        player.play(new Song(selected));
    }
}
