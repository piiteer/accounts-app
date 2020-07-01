package pl.pwasko.accounts.api.nbp_exchange_rates;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Currency;

@FeignClient(name = "nbp-client", url = "http://api.nbp.pl/api/exchangerates/")
public interface NbpExchangeRatesClient {

    @GetMapping("/rates/C/{code}")
    @Headers({"Accept", MediaType.APPLICATION_JSON_VALUE})
    NbpExchangeRatesResponse getRate(@PathVariable String code);

    default NbpExchangeRatesResponse getRate(Currency currency) {
        return getRate(currency.getCurrencyCode());
    }
}
