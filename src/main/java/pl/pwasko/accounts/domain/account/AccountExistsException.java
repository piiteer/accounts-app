package pl.pwasko.accounts.domain.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.pwasko.accounts.domain.pesel.Pesel;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountExistsException extends RuntimeException {
    public AccountExistsException(Pesel pesel) {
        super(String.format("Account already exists for PESEL: %s", pesel.getRaw()));
    }
}
