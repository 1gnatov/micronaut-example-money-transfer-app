package ru.ignatov.bank.services;

import ru.ignatov.bank.exceptions.UserAlreadyCreated;
import ru.ignatov.bank.models.Account;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class AccountRepositoryImpl implements AccountRepository {

    private ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account createAccount(int userId, double balance) {
        if (accounts.containsKey(userId)) {
            throw new UserAlreadyCreated("Account with id: " + userId + " is already created");
        } else {
            Account account = new Account(userId, balance);
            accounts.put(userId, account);
            return account;
        }
    }

    @Override
    public Optional<Account> getAccount(int userId) {
        return Optional.ofNullable(accounts.get(userId));
    }
}
