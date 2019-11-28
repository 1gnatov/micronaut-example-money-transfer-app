package ru.ignatov.bank.models.rest;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ignatov.bank.models.Account;

@Data
@NoArgsConstructor
public class AccountResponse {
    private int userId;
    private double balance;

    public AccountResponse(Account account) {
        userId = account.getUserId();
        balance = account.getBalance().doubleValue();
    }
}
