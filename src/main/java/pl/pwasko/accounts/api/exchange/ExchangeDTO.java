package pl.pwasko.accounts.api.exchange;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Currency;

@Value
public class ExchangeDTO {
    Currency from;
    Currency to;
    BigDecimal amount;
}
