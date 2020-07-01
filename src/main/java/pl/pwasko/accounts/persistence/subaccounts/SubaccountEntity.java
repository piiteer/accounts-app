package pl.pwasko.accounts.persistence.subaccounts;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwasko.accounts.persistence.accounts.AccountEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "subaccount")
@NoArgsConstructor
public class SubaccountEntity {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    AccountEntity account;
    @Column(name = "currency_code")
    String currencyCode;
    @Column(name = "balance")
    BigDecimal balance;

    SubaccountEntity(AccountEntity accountEntity, String currencyCode, BigDecimal balance) {
        this.account = accountEntity;
        this.currencyCode = currencyCode;
        this.balance = balance;
    }
}
