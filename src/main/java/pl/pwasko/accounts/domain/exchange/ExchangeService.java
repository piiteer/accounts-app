package pl.pwasko.accounts.domain.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwasko.accounts.domain.account.Account;
import pl.pwasko.accounts.domain.account.AccountService;
import pl.pwasko.accounts.domain.pesel.Pesel;
import pl.pwasko.accounts.domain.subaccounts.Subaccount;
import pl.pwasko.accounts.domain.subaccounts.SubaccountRepository;
import pl.pwasko.accounts.domain.web.NotFoundException;

import java.math.BigDecimal;
import java.util.Currency;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeService {
    private final RatesService ratesService;
    private final AccountService accountService;
    private final SubaccountRepository subaccountRepository;

    public void exchange(Pesel pesel, Currency sourceCurrency, Currency targetCurrency, BigDecimal amount) {
        Account account = accountService.getAccount(pesel);
        Subaccount sourceSubaccount = getSubaccount(account, sourceCurrency);
        Subaccount targetSubaccount = getSubaccount(account, targetCurrency);

        validateFunds(sourceSubaccount, amount);

        BigDecimal exchangeRate = ratesService.getRate(sourceCurrency, targetCurrency);
        subaccountRepository.substractFromSubaccount(sourceSubaccount, amount);
        subaccountRepository.addToSubaccount(targetSubaccount, amount.multiply(exchangeRate));
    }

    private void validateFunds(Subaccount sourceSubaccount, BigDecimal amount) {
        if (sourceSubaccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
    }

    private Subaccount getSubaccount(Account account, Currency currency) {
        return account.getSubaccounts()
                      .stream()
                      .filter(s -> currency.equals(s.getCurrency()))
                      .findFirst()
                      .orElseThrow(() -> subaccountNotFound(currency));
    }

    private NotFoundException subaccountNotFound(Currency currency) {
        return new NotFoundException("Subaccount not found for currency: " + currency.getCurrencyCode());
    }
}
