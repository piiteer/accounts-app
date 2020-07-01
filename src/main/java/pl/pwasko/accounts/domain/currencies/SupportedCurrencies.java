package pl.pwasko.accounts.domain.currencies;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;

public class SupportedCurrencies {
    public static final Currency PLN = Currency.getInstance("PLN");
    public static final Currency USD = Currency.getInstance("USD");

    public static final List<Currency> ALL_CURRENCIES = Arrays.asList(PLN, USD);

    public static Currency getDefaultCurrency() {
        return PLN;
    }
}
