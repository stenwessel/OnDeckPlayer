package io.github.stenwessel.ondeckplayer.player;

import javafx.util.Duration;

/**
 * @author Sten Wessel
 */
public class PlayerUtil {

    private PlayerUtil() {

    }

    public static String formatDuration(Duration duration) {
        boolean negated = false;
        if (duration.toMillis() < 0) {
            negated = true;
            duration = duration.negate();
        }

        return (negated ? "-" : "") + String.format(
                "%d:%02d",
                (long)duration.toMinutes(),
                (long)(duration.toSeconds() % 60)
        );
    }
}
