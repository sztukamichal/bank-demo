package com.example.bankdemo.account;

import com.example.bankdemo.external.nbp.NBPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final NBPService nbpService;

    @GetMapping("/{id}/balance/EURO")
    @ResponseBody
    public BalanceDTO getAccountBalanceInEuro(@PathVariable("id") Integer id) {
        var account = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account does not exist for id %d", id)));
        var balance = account.getBalance() / nbpService.getEuroExchangeRate();
        return new BalanceDTO(String.format("%.2f EUR", balance));
    }

}
