package pl.pwasko.accounts.api.nbp_exchange_rates;

import lombok.Value;

@Value
public class NbpExchangeRatesResponse {
    String table;
    String currency;
    String code;
    NbpExchangeRatesDTO[] rates;
}
