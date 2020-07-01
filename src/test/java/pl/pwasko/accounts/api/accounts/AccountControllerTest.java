package pl.pwasko.accounts.api.accounts;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.pwasko.accounts.domain.currencies.SupportedCurrencies;
import pl.pwasko.accounts.domain.time.GlobalTime;
import pl.pwasko.accounts.persistence.accounts.AccountEntity;
import pl.pwasko.accounts.persistence.accounts.AccountEntityRepository;
import pl.pwasko.accounts.persistence.subaccounts.SubaccountEntity;
import pl.pwasko.accounts.persistence.subaccounts.SubaccountEntityRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    public static final String PESEL = "85050766777";
    public static final String PESEL2 = "56062781425";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    @Autowired
    private SubaccountEntityRepository subaccountEntityRepository;

    @BeforeEach
    void clearDb() {
        subaccountEntityRepository.deleteAll();
        accountEntityRepository.deleteAll();
    }

    @Test
    void should_create_account() throws Exception {
        // given
        String newAccountPayload = new JSONObject()
                    .put("name", "Luke Skywalker")
                    .put("pesel", PESEL)
                    .toString();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccountPayload));

        // then
        resultActions.andExpect(status().isCreated());

        Optional<AccountEntity> accountEntityOptional = accountEntityRepository.findOneByPesel(PESEL);
        assertThat(accountEntityOptional).isPresent();
        AccountEntity accountEntity = accountEntityOptional.get();
        assertThat(accountEntity.getName()).isEqualTo("Luke Skywalker");
        assertThat(accountEntity.getPesel()).isEqualTo(PESEL);
        assertThat(accountEntity.getSubaccounts()).extracting(SubaccountEntity::getCurrencyCode).containsExactly("PLN", "USD");
    }

    @Test
    void should_create_account_with_initial_amount() throws Exception {
        // given
        String newAccountPayload = new JSONObject()
                .put("name", "Luke Skywalker")
                .put("pesel", PESEL)
                .put("initialAmount", 2000)
                .toString();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccountPayload));

        // then
        resultActions.andExpect(status().isCreated());

        Optional<AccountEntity> accountEntityOptional = accountEntityRepository.findOneByPesel(PESEL);
        assertThat(accountEntityOptional).isPresent();
        AccountEntity accountEntity = accountEntityOptional.get();
        assertThat(accountEntity.getName()).isEqualTo("Luke Skywalker");
        assertThat(accountEntity.getPesel()).isEqualTo(PESEL);
        assertThat(accountEntity.getSubaccounts()).extracting(SubaccountEntity::getCurrencyCode).containsExactly("PLN", "USD");
        assertThat(accountEntity.getSubaccounts()).extracting(SubaccountEntity::getBalance)
                .containsExactly(BigDecimal.valueOf(2000).setScale(2), BigDecimal.valueOf(0).setScale(2));
    }

    @Test
    void should_not_create_account_if_person_not_adult() throws Exception {
        // given
        GlobalTime.useFixedClockAt(LocalDateTime.of(2010, 10, 10, 11, 12));
        String newAccountPayload = new JSONObject()
                .put("name", "Anakin Skywalker")
                .put("pesel", "00260643337")
                .toString();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccountPayload));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void should_get_account() throws Exception {
        // given
        AccountEntity accountEntity = stubAccountEntity();
        accountEntityRepository.save(accountEntity);
        Set<SubaccountEntity> subaccountEntities = stubSubaccountEntities(accountEntity);
        subaccountEntityRepository.saveAll(subaccountEntities);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/accounts/{pesel}", PESEL2));

        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.name").value("Obi Wan Kenobi"))
                     .andExpect(jsonPath("$.pesel").value(PESEL2))
                     .andExpect(jsonPath("$.subaccounts").isMap())
                     .andExpect(jsonPath("$.subaccounts.PLN").value(10000))
                     .andExpect(jsonPath("$.subaccounts.USD").value(10000));
    }

    @Test
    void should_return_not_found_if_account_does_not_exist() throws Exception {
        // given
        // nothing in db

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/accounts/{pesel}", PESEL2));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    private AccountEntity stubAccountEntity() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("Obi Wan Kenobi");
        accountEntity.setPesel(PESEL2);
        return accountEntity;
    }

    private Set<SubaccountEntity> stubSubaccountEntities(AccountEntity accountEntity) {
        return SupportedCurrencies.ALL_CURRENCIES
                           .stream()
                           .map(c -> stubSubaccountEntity(c, accountEntity))
                           .collect(Collectors.toSet());
    }

    private SubaccountEntity stubSubaccountEntity(Currency currency, AccountEntity accountEntity) {
        SubaccountEntity subaccountEntity = new SubaccountEntity();
        subaccountEntity.setAccount(accountEntity);
        subaccountEntity.setCurrencyCode(currency.getCurrencyCode());
        subaccountEntity.setBalance(BigDecimal.valueOf(10_000));
        return subaccountEntity;
    }
}