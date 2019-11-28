package ru.ignatov.bank.services;

import ru.ignatov.bank.models.Account;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;

@Singleton
public interface BankService {
    List<Account> transferMoney(int userIdFrom, int userIdTo, BigDecimal amount);
}
