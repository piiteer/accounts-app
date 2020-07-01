package pl.pwasko.accounts.persistence.subaccounts;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface SubaccountEntityRepository extends CrudRepository<SubaccountEntity, Long> {

    @Modifying
    @Query("UPDATE SubaccountEntity SET balance = :newBalance WHERE id = :id AND balance = :currentBalance")
    int updateBalance(@Param("id") Long id, @Param("currentBalance") BigDecimal currentBalance,
                      @Param("newBalance") BigDecimal newBalance);
}
