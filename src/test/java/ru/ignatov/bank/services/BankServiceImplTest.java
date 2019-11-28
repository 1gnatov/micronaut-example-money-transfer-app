package ru.ignatov.bank.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ignatov.bank.exceptions.NoAccountFound;
import ru.ignatov.bank.exceptions.NoFundsForOperationException;
import ru.ignatov.bank.models.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

    @InjectMocks
    private BankServiceImpl bankService;
    @Mock
    private AccountRepository accountRepository;

    @Test
    void transferMoney() {
        Mockito.when(accountRepository.getAccount(Mockito.anyInt()))
                .thenReturn(Optional.of(new Account(1, 10.)))
                .thenReturn(Optional.of(new Account(2, 10.)));
        List<Account> result = bankService.transferMoney(1, 2, new BigDecimal(5.0));
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertTrue(result.stream().anyMatch(a -> a.getUserId() == 2 && a.getBalance().equals(new BigDecimal(15))));
    }

    @Test
    void transferMoneyNoUserFound() {
        Mockito.when(accountRepository.getAccount(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(NoAccountFound.class, () -> {
            bankService.transferMoney(1, 2, new BigDecimal(5.0));
        });
    }

    @Test
    void transferMoneyNotEnoughFounds() {
        Mockito.when(accountRepository.getAccount(Mockito.anyInt()))
                .thenReturn(Optional.of(new Account(1, 1.)))
                .thenReturn(Optional.of(new Account(2, 10.)));
        assertThrows(NoFundsForOperationException.class, () -> {
            bankService.transferMoney(1, 2, new BigDecimal(5.0));
        });
    }
}