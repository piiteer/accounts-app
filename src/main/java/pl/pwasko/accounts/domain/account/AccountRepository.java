package pl.pwasko.accounts.domain.account;

import pl.pwasko.accounts.domain.pesel.Pesel;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByPesel(Pesel pesel);

    void create(Account newAccount);
}
