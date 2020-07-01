package pl.pwasko.accounts.api.accounts;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;

@Value
@Builder
public class AccountInfoDTO {
    String name;
    String pesel;
    Map<String, BigDecimal> subaccounts;
}
