package pl.pwasko.accounts.api.nbp_exchange_rates;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class NbpExchangeRatesDTO {
    String no;
    String effectiveDate;
    BigDecimal bid;
    BigDecimal ask;
}
