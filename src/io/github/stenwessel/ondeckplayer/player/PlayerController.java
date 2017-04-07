package io.github.stenwessel.ondeckplayer.player;

import io.github.stenwessel.ondeckplayer.Main;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    private static final String UNKNOWN = "Unknown";

    @FXML public Button playButton;

    @FXML public Slider timeSlider;

    @FXML public Label title;
    @FXML public Label artist;
    @FXML public ImageView albumArt;

    private Main main;

    public PlayerController(Main main) {
        this.main = main;
    }

    public void onPlayAction(ActionEvent event) {
        Song song = new Song("E:\\Baseball\\PAA\\Playlists\\Opening Day 2017\\Ed Sheeran - " +
                                     "Castle On The Hill.mp3");
        main.getPlayer().play(song);
    }

    private void playerEmpty() {
        // Reset slider
        timeSlider.setMin(0);
        timeSlider.setMax(0);

        // Reset song metadata
        albumArt.setImage(null);
        title.setText(null);
        artist.setText(null);
    }

    private void newSongLoaded(Song song) {
        MediaPlayer mp = song.getMediaPlayer();

        mp.setOnReady(() -> {
            // Reset slider length
            timeSlider.setMin(mp.getStartTime().toMillis());
            timeSlider.setMax(mp.getStopTime().toMillis());

            // Get metadata
            ObservableMap<String, Object> metadata = mp.getMedia().getMetadata();
            albumArt.setImage((Image)metadata.get("image"));
            String mTitle = (String)metadata.get("title");
            title.setText(mTitle != null ? mTitle : UNKNOWN);
            String mArtist = (String)metadata.get("artist");
            artist.setText(mArtist != null ? mArtist : UNKNOWN);
        });
    }

    private void updateControls(Song song) {
        MediaPlayer mp = song.getMediaPlayer();

        // Current time
        Duration currentTime = mp.getCurrentTime();
        timeSlider.adjustValue(currentTime.toMillis());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Player player = main.getPlayer();

        player.currentSongProperty().addListener((o, old, song) -> {
            if (song == null) {
                playerEmpty();
            } else {
                newSongLoaded(song);
            }
        });

        player.currentTimeProperty().addListener((obs, o, n) -> updateControls(player.getCurrentSong()));
    }
}
