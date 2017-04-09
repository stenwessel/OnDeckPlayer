package io.github.stenwessel.ondeckplayer.player;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Player that plays songs.
 *
 * @author Sten Wessel
 */
public class Player {

    private ObjectProperty<Song> currentSongProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Status> statusProperty = new SimpleObjectProperty<>(Status.EMPTY);

    // Delegated properties
    private ObjectProperty<Duration> currentTimeProperty = new SimpleObjectProperty<>(Duration.UNKNOWN);
    private DoubleProperty volumeProperty = new SimpleDoubleProperty(1);

    /**
     * Loads a new song into the player. Also loads the song's MediaPlayer.
     *
     * @param song
     *         The song to load.
     */
    public void load(Song song) {
        if (getCurrentSong() != null) {
            getCurrentSong().getMediaPlayer().volumeProperty().unbindBidirectional(volumeProperty);
            getCurrentSong().dispose();
        }
        song.load();
        currentSongProperty.set(song);
        currentTimeProperty.bind(song.getMediaPlayer().currentTimeProperty());
        song.getMediaPlayer().volumeProperty().bindBidirectional(volumeProperty);
    }

    /**
     * Sets the current song and plays it.
     *
     * @param song
     *         The song to play.
     */
    public void play(Song song) {
        load(song);
        play();
    }

    /**
     * Plays the current song.
     *
     * @see MediaPlayer#play()
     */
    public void play() {
        if (getCurrentSong() != null) {
            getCurrentSong().getMediaPlayer().play();
            statusProperty.set(Status.PLAYING);
        }
    }

    /**
     * Pauses the current song.
     *
     * @see MediaPlayer#pause()
     */
    public void pause() {
        if (getCurrentSong() != null) {
            getCurrentSong().getMediaPlayer().pause();
            statusProperty.set(Status.PAUSED);
        }
    }

    /**
     * Stops the current song.
     *
     * @see MediaPlayer#stop()
     */
    public void stop() {
        if (getCurrentSong() != null) {
            getCurrentSong().getMediaPlayer().stop();
            statusProperty.set(Status.STOPPED);
        }
    }

    /**
     * Seeks the current song.
     *
     * @param seekTime
     *         Time to seek to.
     * @see MediaPlayer#seek(Duration)
     */
    public void seek(Duration seekTime) {
        if (getCurrentSong() != null) {
            getCurrentSong().getMediaPlayer().seek(seekTime);
        }
    }

    public enum Status {
        PLAYING,
        PAUSED,
        STOPPED,
        EMPTY
    }

    public ReadOnlyObjectProperty<Song> currentSongProperty() {
        return currentSongProperty;
    }

    public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
        return currentTimeProperty;
    }

    public ReadOnlyObjectProperty<Status> statusProperty() {
        return statusProperty;
    }

    public Status getStatus() {
        return statusProperty.get();
    }

    public DoubleProperty volumeProperty() {
        return volumeProperty;
    }

    public Song getCurrentSong() {
        return currentSongProperty.get();
    }
}
