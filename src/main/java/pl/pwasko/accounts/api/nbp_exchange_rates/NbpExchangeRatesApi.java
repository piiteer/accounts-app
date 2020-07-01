package pl.pwasko.accounts.api.nbp_exchange_rates;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pwasko.accounts.domain.exchange.ExchangeRatesApi;

import java.math.BigDecimal;
import java.util.Currency;

@Service
@RequiredArgsConstructor
public class NbpExchangeRatesApi implements ExchangeRatesApi {

    private final NbpExchangeRatesClient nbpClient;

    @Override
    public BigDecimal getAskRate(Currency currency) {
        return getRates(currency).getAsk();
    }

    @Override
    public BigDecimal getBidRate(Currency currency) {
        return getRates(currency).getBid();
    }

    private NbpExchangeRatesDTO getRates(Currency currency) {
        return nbpClient.getRate(currency).getRates()[0];
    }
}
