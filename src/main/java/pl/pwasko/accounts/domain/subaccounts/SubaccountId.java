package pl.pwasko.accounts.domain.subaccounts;

import lombok.Value;

@Value(staticConstructor = "of")
public class SubaccountId {
    Long raw;
}
