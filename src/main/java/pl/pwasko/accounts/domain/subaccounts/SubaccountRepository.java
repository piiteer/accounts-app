package pl.pwasko.accounts.domain.subaccounts;

import pl.pwasko.accounts.persistence.accounts.AccountEntity;

import java.math.BigDecimal;
import java.util.List;

public interface SubaccountRepository {

    void saveSubaccounts(List<Subaccount> subaccounts, AccountEntity accountEntity);

    void substractFromSubaccount(Subaccount subaccount, BigDecimal amount);

    void addToSubaccount(Subaccount subaccount, BigDecimal amount);
}
