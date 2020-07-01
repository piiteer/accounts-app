package pl.pwasko.accounts.persistence.subaccounts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pwasko.accounts.domain.subaccounts.Subaccount;
import pl.pwasko.accounts.domain.subaccounts.SubaccountRepository;
import pl.pwasko.accounts.persistence.accounts.AccountEntity;

import java.math.BigDecimal;
import java.util.ConcurrentModificationException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InMemorySubaccountRepository implements SubaccountRepository {

    private final SubaccountEntityRepository entityRepository;
    private final SubaccountMapper subaccountMapper;

    @Override
    public void substractFromSubaccount(Subaccount subaccount, BigDecimal amount) {
        int rowsModified = entityRepository.updateBalance(subaccount.getId().getRaw(), subaccount.getBalance(), subaccount.getBalance().subtract(amount));
        if (rowsModified == 0) {
            throw new ConcurrentModificationException("Balance modified by another transaction");
        }
    }

    @Override
    public void addToSubaccount(Subaccount subaccount, BigDecimal amount) {
        int rowsModified = entityRepository.updateBalance(subaccount.getId().getRaw(), subaccount.getBalance(), subaccount.getBalance().add(amount));
        if (rowsModified == 0) {
            throw new ConcurrentModificationException("Balance modified by another transaction");
        }
    }

    @Override
    public void saveSubaccounts(List<Subaccount> subaccounts, AccountEntity accountEntity) {
            entityRepository.saveAll(subaccountMapper.toEntity(subaccounts, accountEntity));
    }
}
