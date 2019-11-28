package ru.ignatov.bank.services;

import ru.ignatov.bank.models.Account;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public interface AccountRepository {
    Account createAccount(int userId, double balance);

    Optional<Account> getAccount(int userId);
}
