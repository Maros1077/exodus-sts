package cz.exodus.sts.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class DateUtils {

        public static boolean isInPast(Date givenDate) {
            return givenDate.before(new Date());
        }
        public static long getSecondsBetweenDates(Date givenDate) {
            Instant now = Instant.now();
            Instant givenInstant = givenDate.toInstant();
            Duration duration = Duration.between(now, givenInstant);
            return Math.abs(duration.getSeconds());
        }
}
