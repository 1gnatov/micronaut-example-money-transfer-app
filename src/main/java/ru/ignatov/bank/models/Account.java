package ru.ignatov.bank.models;

import lombok.Data;
import ru.ignatov.bank.exceptions.NoFundsForOperationException;

import java.math.BigDecimal;

@Data
public class Account {
    private int userId;
    private BigDecimal balance;
    private Object lock = new Object();

    public Account(int userId, double balance) {
        this.userId = userId;
        this.balance = new BigDecimal(balance);
    }

    public void addFunds(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    public void removeFunds(BigDecimal amount) throws NoFundsForOperationException {
        if (balance.compareTo(amount) >= 0) {
            this.balance = balance.subtract(amount);
        } else {
            throw new NoFundsForOperationException(balance, amount);
        }
    }
}
