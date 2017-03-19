package io.github.stenwessel.ondeckplayer.player;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URI;

/**
 * Represents a playable song.
 *
 * @author Sten Wessel
 */
public class Song {

    private final URI uri;
    private MediaPlayer mediaPlayer;

    /**
     * Creates a song based on a playable file.
     *
     * @param uri
     *         The URI referencing a playable file. This cannot be {@code null}.
     */
    public Song(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("URI cannot be null");
        }

        this.uri = uri;
    }

    /**
     * Creates a song based on a playable file.
     *
     * @param file
     *         A playable file.
     */
    public Song(File file) {
        this(file.toURI());
    }

    /**
     * Creates a song based on a playable file.
     *
     * @param filePath
     *         A path to a local file.
     */
    public Song(String filePath) {
        this(new File(filePath));
    }

    /**
     * Loads the playable file.
     * <p>
     * This creates a {@link MediaPlayer} object for the song, accessible through
     * {@link Song#getMediaPlayer()}.
     */
    public void load() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer(new Media(uri.toString()));
        }
    }

    /**
     * Disposes the {@link MediaPlayer} for this song.
     * <p>
     * Disposes and dereferences the {@link MediaPlayer} to free resources.
     *
     * @see MediaPlayer#dispose()
     */
    public void dispose() {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /**
     * Returns a {@link MediaPlayer} that plays this song.
     * <p>
     * Note that the song needs to be loaded via {@link Song#load()} first
     *
     * @return A {@link MediaPlayer} object for this song, or {@code null} when the song is not
     * loaded.
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
