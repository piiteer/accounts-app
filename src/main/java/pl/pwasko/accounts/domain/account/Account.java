package pl.pwasko.accounts.domain.account;

import lombok.Builder;
import lombok.Value;
import pl.pwasko.accounts.domain.pesel.Pesel;
import pl.pwasko.accounts.domain.subaccounts.Subaccount;

import java.util.List;

@Value
@Builder
public class Account {
    Pesel pesel;
    String name;
    List<Subaccount> subaccounts;
}
