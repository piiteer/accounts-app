package pl.pwasko.accounts.api.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pwasko.accounts.domain.exchange.ExchangeService;
import pl.pwasko.accounts.domain.pesel.Pesel;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts/{pesel:[0-9]{11}}/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PutMapping
    void exchange(@PathVariable String pesel, @RequestBody @Valid ExchangeDTO exchangeDTO) {
        exchangeService.exchange(Pesel.of(pesel), exchangeDTO.getFrom(), exchangeDTO.getTo(), exchangeDTO.getAmount());
    }
}
