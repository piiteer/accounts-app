package pl.pwasko.accounts.persistence.accounts;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountEntityRepository extends CrudRepository<AccountEntity, Long> {
    Optional<AccountEntity> findOneByPesel(String pesel);
}
