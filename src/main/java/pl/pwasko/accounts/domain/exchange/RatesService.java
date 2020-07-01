package pl.pwasko.accounts.domain.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pwasko.accounts.domain.currencies.SupportedCurrencies;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;

@Service
@RequiredArgsConstructor
public class RatesService {

    private final ExchangeRatesApi ratesApi;

    BigDecimal getRate(Currency sourceCurrency, Currency targetCurrency) {
        if (sourceCurrency.equals(SupportedCurrencies.getDefaultCurrency())) {
            return ratesApi.getBidRate(targetCurrency);
        }
        return BigDecimal.ONE.divide(ratesApi.getAskRate(sourceCurrency), MathContext.DECIMAL32);
    }
}
