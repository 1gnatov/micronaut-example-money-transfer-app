package ru.ignatov.bank.services;

import lombok.RequiredArgsConstructor;
import ru.ignatov.bank.exceptions.NoAccountFound;
import ru.ignatov.bank.models.Account;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final AccountRepository accountRepository;

    @Override
    public List<Account> transferMoney(int userIdFrom, int userIdTo, BigDecimal amount) {
        Account accountFrom = accountRepository.getAccount(userIdFrom).orElseThrow(() -> new NoAccountFound(userIdFrom));
        Account accountTo = accountRepository.getAccount(userIdTo).orElseThrow(() -> new NoAccountFound(userIdTo));
        Account blockFirst = accountFrom.getUserId() < accountTo.getUserId() ? accountFrom : accountTo;
        Account blockLast = blockFirst.equals(accountFrom) ? accountTo : accountFrom;
        synchronized (blockFirst.getLock()) {
            synchronized (blockLast.getLock()) {
                accountFrom.removeFunds(amount);
                accountTo.addFunds(amount);
            }
        }
        return List.of(accountFrom, accountTo);
    }
}
