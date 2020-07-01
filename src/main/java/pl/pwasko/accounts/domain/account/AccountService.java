package pl.pwasko.accounts.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwasko.accounts.domain.currencies.SupportedCurrencies;
import pl.pwasko.accounts.domain.pesel.Pesel;
import pl.pwasko.accounts.domain.pesel.PeselService;
import pl.pwasko.accounts.domain.subaccounts.Subaccount;
import pl.pwasko.accounts.domain.time.GlobalTime;
import pl.pwasko.accounts.domain.web.NotFoundException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PeselService peselService;

    @Transactional
    public void createAccount(Pesel pesel, String name, BigDecimal providedInitialAmount) {
        peselService.validatePesel(pesel);
        validateAccountNotPresent(pesel);
        BigDecimal initialAmount = providedInitialAmount == null ? BigDecimal.ZERO : providedInitialAmount;
        validateInitialAmount(initialAmount);
        validateAge(pesel);

        List<Subaccount> subaccounts = SupportedCurrencies.ALL_CURRENCIES
                                                          .stream()
                                                          .map(currency -> createSubaccount(currency, initialAmount))
                                                          .collect(Collectors.toList());

        accountRepository.create(newAccount(pesel, name, subaccounts));
    }

    public Account getAccount(Pesel pesel) {
        return accountRepository.findByPesel(pesel)
                                .orElseThrow(() -> notFound(pesel));
    }

    private void validateAccountNotPresent(Pesel pesel) {
        accountRepository.findByPesel(pesel)
                         .ifPresent(account -> throwAccountExists(pesel));
    }

    private void validateInitialAmount(BigDecimal initialAmount) {
        if (initialAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidInitialAmountException("Initial amount must be grater than zero");
        }
    }

    private void validateAge(Pesel pesel) {
        if (GlobalTime.now().isBefore(pesel.getBrithDate().plusYears(18))) {
            throw new ClientTooYoungException("Client must be 18 or older");
        }
    }

    private void throwAccountExists(Pesel pesel) {
        throw new AccountExistsException(pesel);
    }

    private Account newAccount(Pesel pesel, String name, List<Subaccount> subaccounts) {
        return Account.builder()
                      .pesel(pesel)
                      .name(name)
                      .subaccounts(subaccounts)
                      .build();
    }

    private Subaccount createSubaccount(Currency currency, BigDecimal initialAmount) {
        return Subaccount.builder()
                         .currency(currency)
                         .balance(SupportedCurrencies.getDefaultCurrency().equals(currency) ? initialAmount : BigDecimal.ZERO)
                         .build();
    }

    private NotFoundException notFound(Pesel pesel) {
        return new NotFoundException("Account not found for PESEL: " + pesel.getRaw());
    }
}
