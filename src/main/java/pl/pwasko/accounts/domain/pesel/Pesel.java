package pl.pwasko.accounts.domain.pesel;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class Pesel {
    @NonNull
    String raw;

    public LocalDateTime getBrithDate() {
        return LocalDateTime.of(getYear(), getMonth(), getDay(), 0, 0);
    }

    private int getCentury() {
        int month = Integer.parseInt(raw.substring(2, 4));
        if (month < 20) {
            return 1900;
        }
        if (month < 40) {
            return 2000;
        }
        if (month < 60) {
            return 2100;
        }
        if (month < 80) {
            return 2200;
        }
        return 1800;
    }

    private int getYear() {
        int century = getCentury();
        return century + Integer.parseInt(raw.substring(0, 2));
    }

    private int getMonth() {
        int monthNumber = Integer.parseInt(raw.substring(2, 4));
        while (monthNumber > 12) {
            monthNumber -= 20;
        }
        return monthNumber;
    }

    private int getDay() {
        return Integer.parseInt(raw.substring(4, 6));
    }
}
