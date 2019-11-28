package ru.ignatov.bank.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.ignatov.bank.exceptions.NoAccountFound;
import ru.ignatov.bank.models.Account;
import ru.ignatov.bank.models.rest.AccountResponse;
import ru.ignatov.bank.models.rest.BalanceResponse;
import ru.ignatov.bank.models.rest.CreateAccountRequest;
import ru.ignatov.bank.models.rest.TransferMoneyRequest;
import ru.ignatov.bank.models.rest.TransferMoneyResponse;
import ru.ignatov.bank.services.AccountRepository;
import ru.ignatov.bank.services.BankService;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Slf4j
@Controller("/bank")
@RequiredArgsConstructor
public class BankController {
    private final BankService bank;
    private final AccountRepository accountRepository;

    @Post("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountResponse createAccount(@Valid CreateAccountRequest request) {
        return new AccountResponse(accountRepository.createAccount(request.getUserId(), request.getBalance()));
    }

    @Get("/account/balance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BalanceResponse getBalance(int userId) {
        Account account = accountRepository.getAccount(userId).orElseThrow(() -> new NoAccountFound(userId));
        return new BalanceResponse(account.getBalance());
    }

    @Post("/transfer")
    public TransferMoneyResponse transferMoney(@Valid TransferMoneyRequest request) {
        return new TransferMoneyResponse(bank.transferMoney(request.getUserIdFrom(), request.getUserIdTo(), request.getAmount())
                .stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList()));
    }
}
