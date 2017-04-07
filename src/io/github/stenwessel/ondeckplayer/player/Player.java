package io.github.stenwessel.ondeckplayer.player;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Player that plays songs.
 *
 * @author Sten Wessel
 */
public class Player {

    private Song currentSong;

    /**
     * Loads a new song into the player. Also loads the song's MediaPlayer.
     *
     * @param song
     *         The song to load.
     */
    public void load(Song song) {
        if (currentSong != null) {
            currentSong.dispose();
        }
        currentSong = song;
        currentSong.load();
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
        if (currentSong != null) {
            currentSong.getMediaPlayer().play();
        }
    }

    /**
     * Pauses the current song.
     *
     * @see MediaPlayer#pause()
     */
    public void pause() {
        if (currentSong != null) {
            currentSong.getMediaPlayer().pause();
        }
    }

    /**
     * Stops the current song.
     *
     * @see MediaPlayer#stop()
     */
    public void stop() {
        if (currentSong != null) {
            currentSong.getMediaPlayer().stop();
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
        if (currentSong != null) {
            currentSong.getMediaPlayer().seek(seekTime);
        }
    }

    public Song getCurrentSong() {
        return currentSong;
    }
}
