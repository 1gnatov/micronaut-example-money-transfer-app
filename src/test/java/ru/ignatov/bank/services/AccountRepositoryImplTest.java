package ru.ignatov.bank.services;

import org.junit.jupiter.api.Test;
import ru.ignatov.bank.exceptions.UserAlreadyCreated;
import ru.ignatov.bank.models.Account;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AccountRepositoryImplTest {

    private AccountRepository accountRepository = new AccountRepositoryImpl();

    @Test
    void testNewAccountCreation() {
        Account acc = accountRepository.createAccount(1, 10.);
        assertNotNull(acc);
        assertEquals(acc.getUserId(), 1);
        assertEquals(acc.getBalance(), new BigDecimal(10.));
    }

    @Test
    void testNewAccountAlreadyCreated() {
        Account acc = accountRepository.createAccount(1, 10.);
        assertNotNull(acc);
        assertThrows(UserAlreadyCreated.class, () -> {
            accountRepository.createAccount(1, 20.);
        });
    }

    @Test
    void testAccountGet() {
        accountRepository.createAccount(1, 10.);
        var acc = accountRepository.getAccount(1);
        assertTrue(acc.isPresent());
        assertEquals(acc.get().getUserId(), 1);
    }

}