package pl.pwasko.accounts.persistence.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pwasko.accounts.domain.account.Account;
import pl.pwasko.accounts.domain.account.AccountRepository;
import pl.pwasko.accounts.domain.pesel.Pesel;
import pl.pwasko.accounts.domain.subaccounts.SubaccountRepository;
import pl.pwasko.accounts.persistence.subaccounts.SubaccountMapper;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InMemoryAccountRepository implements AccountRepository {

    private final AccountEntityRepository entityRepository;
    private final SubaccountRepository subaccountRepository;
    private final SubaccountMapper subaccountMapper;

    @Override
    public Optional<Account> findByPesel(Pesel pesel) {
        return entityRepository.findOneByPesel(pesel.getRaw())
                               .map(this::toDomain);
    }

    @Override
    public void create(Account newAccount) {
        AccountEntity accountEntity = toEntity(newAccount);
        entityRepository.save(accountEntity);
        subaccountRepository.saveSubaccounts(newAccount.getSubaccounts(), accountEntity);
    }

    private AccountEntity toEntity(Account account) {
        return new AccountEntity(account.getPesel().getRaw(),
                                 account.getName());
    }

    private Account toDomain(AccountEntity accountEntity) {
        return Account.builder()
                      .name(accountEntity.getName())
                      .pesel(Pesel.of(accountEntity.getPesel()))
                      .subaccounts(subaccountMapper.toDomain(accountEntity.getSubaccounts()))
                      .build();
    }
}
