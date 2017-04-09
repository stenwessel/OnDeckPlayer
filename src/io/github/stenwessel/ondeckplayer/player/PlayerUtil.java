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

        double dRemSeconds = duration.toSeconds() % 60;
        long remSeconds;
        if (negated) {
            remSeconds = (long)Math.ceil(dRemSeconds);
        } else {
            remSeconds = (long)dRemSeconds;
        }


        return (negated ? "-" : "") + String.format(
                "%d:%02d",
                (long)duration.toMinutes(),
                remSeconds
        );
    }
}
