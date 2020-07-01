package pl.pwasko.accounts.domain.exchange;

import java.math.BigDecimal;
import java.util.Currency;

public interface ExchangeRatesApi {

    BigDecimal getAskRate(Currency currency);

    BigDecimal getBidRate(Currency currency);
}
