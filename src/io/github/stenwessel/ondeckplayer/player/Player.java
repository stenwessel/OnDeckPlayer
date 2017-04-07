package io.github.stenwessel.ondeckplayer.player;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
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
    private ObjectProperty<Duration> currentTimeProperty = new SimpleObjectProperty<>(Duration.UNKNOWN);

    /**
     * Loads a new song into the player. Also loads the song's MediaPlayer.
     *
     * @param song
     *         The song to load.
     */
    public void load(Song song) {
        if (getCurrentSong() != null) {
            getCurrentSong().dispose();
        }
        song.load();
        currentSongProperty.set(song);
        currentTimeProperty.bind(song.getMediaPlayer().currentTimeProperty());
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

    public ReadOnlyObjectProperty<Song> currentSongProperty() {
        return currentSongProperty;
    }

    public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
        return currentTimeProperty;
    }

    public Song getCurrentSong() {
        return currentSongProperty.get();
    }
}
