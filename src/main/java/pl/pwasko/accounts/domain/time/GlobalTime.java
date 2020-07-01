package pl.pwasko.accounts.domain.time;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class GlobalTime {

    private static Clock clock = Clock.systemDefaultZone();
    private static ZoneId zoneId = ZoneId.systemDefault();

    public static LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public static void useFixedClockAt(LocalDateTime time) {
        clock = Clock.fixed(time.atZone(zoneId).toInstant(), zoneId);
    }

}
