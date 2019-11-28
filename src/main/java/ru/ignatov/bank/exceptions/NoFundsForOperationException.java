package ru.ignatov.bank.exceptions;

import java.math.BigDecimal;

public class NoFundsForOperationException extends BusinessLogicException {
    public NoFundsForOperationException(BigDecimal balance, BigDecimal amount) {
        super(String.format("No founds for operation, %.2f need: %.2f", balance.doubleValue(), amount.doubleValue()));
    }
}
