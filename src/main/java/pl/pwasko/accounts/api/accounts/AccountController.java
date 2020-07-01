package pl.pwasko.accounts.api.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pwasko.accounts.domain.account.Account;
import pl.pwasko.accounts.domain.account.AccountService;
import pl.pwasko.accounts.domain.pesel.Pesel;
import pl.pwasko.accounts.domain.subaccounts.Subaccount;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{pesel:[0-9]{11}}")
    public AccountInfoDTO getAccount(@PathVariable String pesel) {
        return toDto(accountService.getAccount(Pesel.of(pesel)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody @Valid AccountCreateDTO createDTO) {
        accountService.createAccount(Pesel.of(createDTO.getPesel()), createDTO.getName(), createDTO.getInitialAmount());
    }

    private AccountInfoDTO toDto(Account account) {
        return AccountInfoDTO.builder()
                             .name(account.getName())
                             .pesel(account.getPesel().getRaw())
                             .subaccounts(mapOfSubaccounts(account.getSubaccounts()))
                             .build();
    }

    private Map<String, BigDecimal> mapOfSubaccounts(List<Subaccount> subaccounts) {
        return subaccounts.stream()
                          .collect(Collectors.toMap(sa -> sa.getCurrency().getCurrencyCode(),
                                                    Subaccount::getBalance));
    }
}
