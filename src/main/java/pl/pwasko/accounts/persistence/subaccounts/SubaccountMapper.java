package pl.pwasko.accounts.persistence.subaccounts;

import org.springframework.stereotype.Service;
import pl.pwasko.accounts.domain.subaccounts.Subaccount;
import pl.pwasko.accounts.domain.subaccounts.SubaccountId;
import pl.pwasko.accounts.persistence.accounts.AccountEntity;

import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubaccountMapper {

    Set<SubaccountEntity> toEntity(List<Subaccount> subaccounts, AccountEntity accountEntity) {
        return subaccounts.stream().map(t -> toEntity(t, accountEntity)).collect(Collectors.toSet());
    }

    SubaccountEntity toEntity(Subaccount subaccount, AccountEntity accountEntity) {
        return new SubaccountEntity(accountEntity, subaccount.getCurrency().getCurrencyCode(), subaccount.getBalance());
    }


    public List<Subaccount> toDomain(Set<SubaccountEntity> subaccounts) {
        return subaccounts.stream().map(this::toDomain).collect(Collectors.toList());
    }

    private Subaccount toDomain(SubaccountEntity subaccountEntity) {
        return Subaccount.builder()
                .id(SubaccountId.of(subaccountEntity.getId()))
                .currency(Currency.getInstance(subaccountEntity.getCurrencyCode()))
                .balance(subaccountEntity.getBalance())
                .build();
    }
}
