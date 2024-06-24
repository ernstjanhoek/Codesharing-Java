package platform.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class SnippetUtils {
    static public long calculateRemainingTimeInSeconds(Date date, int timeLimit) {
        Instant limitInstant = date.toInstant().plusSeconds(timeLimit - 1);
        return Duration.between(Instant.now(), limitInstant).toSeconds();
    }
}
