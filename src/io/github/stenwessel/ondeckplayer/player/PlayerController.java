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
    private static final Image EMPTY_IMAGE = new Image("/img/vinylbaseball.png");

    private final Player player;
    private ResourceBundle bundle;

    @FXML public Button playButton;
    @FXML public Button stopButton;
    @FXML public Slider volumeSlider;

    @FXML public Slider timeSlider;
    @FXML public Label currentTime;
    @FXML public Label negativeTime;
    @FXML public Label totalDuration;

    @FXML public Label title;
    @FXML public Label artist;
    @FXML public ImageView albumArt;

    public PlayerController(Main main) {
        this.player = main.getPlayer();
    }

    public void onPlayAction(ActionEvent event) {
        if (player.getStatus() == Player.Status.PLAYING) {
            player.pause();
            playButton.setText(bundle.getString("fa.play"));
        } else {
            player.play();
            playButton.setText(bundle.getString("fa.pause"));
        }
    }

    private void playerEmpty() {
        // Reset slider
        timeSlider.setMin(0);
        timeSlider.setMax(0);

        // Disable controls
        timeSlider.setDisable(true);
        playButton.setDisable(true);
        stopButton.setDisable(true);

        // Reset song metadata
        albumArt.setImage(EMPTY_IMAGE);
        title.setText(null);
        artist.setText(null);
    }

    private void newSongLoaded(Song song) {
        MediaPlayer mp = song.getMediaPlayer();

        mp.setOnReady(() -> {
            // Set slider length
            timeSlider.setMin(mp.getStartTime().toMillis());
            timeSlider.setMax(mp.getStopTime().toMillis());

            // Set song duration
            totalDuration.setText(PlayerUtil.formatDuration(mp.getStopTime()));

            // Enable controls
            timeSlider.setDisable(false);
            playButton.setDisable(false);
            stopButton.setDisable(false);

            // Get metadata
            ObservableMap<String, Object> metadata = mp.getMedia().getMetadata();
            Image mImage = (Image)metadata.get("image");
            albumArt.setImage(mImage != null ? mImage : EMPTY_IMAGE);
            String mTitle = (String)metadata.get("title");
            title.setText(mTitle != null ? mTitle : UNKNOWN);
            String mArtist = (String)metadata.get("artist");
            artist.setText(mArtist != null ? mArtist : UNKNOWN);
        });
    }

    private void updateControls(Song song) {
        MediaPlayer mp = song.getMediaPlayer();

        // Current time
        Duration duration = mp.getCurrentTime();
        if (!timeSlider.isValueChanging()) {
            timeSlider.setValue(duration.toMillis());
        }
        currentTime.setText(PlayerUtil.formatDuration(duration));
        negativeTime.setText(PlayerUtil.formatDuration(mp.getStopTime().subtract(duration).negate()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        // Song and time listeners
        player.currentSongProperty().addListener((o, old, song) -> {
            if (song == null) {
                playerEmpty();
            } else {
                newSongLoaded(song);
            }
        });

        player.currentTimeProperty().addListener((obs, o, n) -> updateControls(player.getCurrentSong()));
        playerEmpty();

        // Control bindings
        timeSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging) {
                player.seek(Duration.millis(timeSlider.getValue()));
            }
        });

        volumeSlider.valueProperty().bindBidirectional(player.volumeProperty());
    }
}
