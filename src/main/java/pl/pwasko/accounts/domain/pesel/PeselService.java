package pl.pwasko.accounts.domain.pesel;

import org.springframework.stereotype.Service;

@Service
public class PeselService {

    private static final int[] MODIFIERS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1};
    private static final int PESEL_LENGTH = 11;

    public void validatePesel(Pesel pesel) {
        validateLength(pesel);
        validateOnlyDigits(pesel);
        validateControlNumber(pesel);
    }

    private void validateLength(Pesel pesel) {
        if (pesel.getRaw().length() != PESEL_LENGTH) {
            throw new InvalidPeselException("Invalid PESEL length");
        }
    }

    private void validateOnlyDigits(Pesel pesel) {
        if (!pesel.getRaw().chars().allMatch(Character::isDigit)) {
            throw new InvalidPeselException("PESEL contains invalid characters");
        }
    }

    private void validateControlNumber(Pesel pesel) {
        // Assuming all PESEL numbers are valid. In reality it might not be the case...
        char[] chars = pesel.getRaw().toCharArray();

        int sum = 0;
        for (int i = 0; i < PESEL_LENGTH; i++) {
            sum += digitValue(chars[i]) * MODIFIERS[i];
        }

        if (sum % 10 != 0) {
            throw new InvalidPeselException("Invalid PESEL control number");
        }
    }

    private int digitValue(char aChar) {
        return aChar - '0';
    }
}
