package pl.pwasko.accounts.persistence.accounts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pwasko.accounts.persistence.subaccounts.SubaccountEntity;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String pesel;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "account", targetEntity = SubaccountEntity.class, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<SubaccountEntity> subaccounts;

    AccountEntity(String pesel, String name) {
        this.pesel = pesel;
        this.name = name;
    }
}

