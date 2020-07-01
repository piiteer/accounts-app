package pl.pwasko.accounts.domain.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientTooYoungException extends RuntimeException {
    public ClientTooYoungException(String message) {
        super(message);
    }
}
