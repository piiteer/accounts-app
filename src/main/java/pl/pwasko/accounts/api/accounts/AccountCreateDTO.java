package pl.pwasko.accounts.api.accounts;

import lombok.Value;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Value
public class AccountCreateDTO {
    String name;
    @Size(min = 11, max = 11)
    @Pattern(regexp = "[0-9]{11}")
    String pesel;

    BigDecimal initialAmount;
}