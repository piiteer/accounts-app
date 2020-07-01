package pl.pwasko.accounts.domain.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInitialAmountException extends RuntimeException {
    public InvalidInitialAmountException(String message) {
        super(message);
    }
}
