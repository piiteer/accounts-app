package pl.pwasko.accounts.domain.subaccounts;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Currency;

@Value
@Builder
public class Subaccount {
    SubaccountId id;
    Currency currency;
    BigDecimal balance;
}
