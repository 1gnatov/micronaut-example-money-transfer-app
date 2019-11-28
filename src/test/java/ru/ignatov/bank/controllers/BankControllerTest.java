package ru.ignatov.bank.controllers;

import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ignatov.bank.models.Account;
import ru.ignatov.bank.models.rest.AccountResponse;
import ru.ignatov.bank.models.rest.BalanceResponse;
import ru.ignatov.bank.models.rest.CreateAccountRequest;
import ru.ignatov.bank.models.rest.TransferMoneyRequest;
import ru.ignatov.bank.models.rest.TransferMoneyResponse;
import ru.ignatov.bank.services.AccountRepository;
import ru.ignatov.bank.services.AccountRepositoryImpl;
import ru.ignatov.bank.services.BankService;
import ru.ignatov.bank.services.BankServiceImpl;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;

@MicronautTest()
@Property(name = "micronaut.server.port", value = "-1")
class BankControllerTest {

    @Inject
    AccountRepository accountRepository;
    @Inject
    BankService bankService;
    @Inject
    @Client(value = "/bank", errorType = HttpClientResponseException.class)
    RxHttpClient client;

    @MockBean(BankServiceImpl.class)
    BankService bankService() {
        return Mockito.mock(BankService.class);
    }
    @MockBean(AccountRepositoryImpl.class)
    AccountRepository accountRepository() {
        return Mockito.mock(AccountRepository.class);
    }

    @BeforeEach
    void refreshMocks() {
        Mockito.reset(accountRepository, bankService);
    }

    @Test
    void testAccountCreation() {
        Mockito.when(accountRepository.createAccount(Mockito.anyInt(), Mockito.anyDouble())).thenReturn(new Account(1, 10.));
        var request = postJson("/account", new CreateAccountRequest(1, 10.));
        var body = client.toBlocking().exchange(request, AccountResponse.class).body();
        assertNotNull(body);
        assertEquals(1, body.getUserId());
        assertEquals(10.0, body.getBalance());
        Mockito.verify(accountRepository, atLeastOnce()).createAccount(Mockito.anyInt(), Mockito.anyDouble());
    }

    @Test
    void testAccountCreationNoUserField() {
        var request = postJson("/account", "{\"balance\":100.00}");

        //https://github.com/micronaut-projects/micronaut-core/pull/2372
        assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(request, AccountResponse.class);
        });
    }

    @Test
    void testGetBalance() {
        Mockito.when(accountRepository.getAccount(Mockito.anyInt())).thenReturn(Optional.of(new Account(1, 10.)));
        var request = HttpRequest.GET("/account/balance?userId=1");
        var body = client.toBlocking().exchange(request, BalanceResponse.class).body();
        assertNotNull(body);
        assertNotNull(body.getBalance());
        assertEquals(10., body.getBalance().doubleValue());
        Mockito.verify(accountRepository, atLeastOnce()).getAccount(Mockito.anyInt());
    }

    @Test
    void testTransfer() {
        Mockito.when(bankService.transferMoney(1, 2, BigDecimal.valueOf(4.0)))
                .thenReturn(List.of(new Account(1, 6.0), new Account(2, 14.0)));
        var request = postJson("/transfer", new TransferMoneyRequest(1, 2, BigDecimal.valueOf(4.0)));
        var body = client.toBlocking().exchange(request, TransferMoneyResponse.class).body();
        assertNotNull(body);
        assertNotNull(body.getAccounts());
        assertEquals(2, body.getAccounts().size());
        assertTrue(body.getAccounts().stream().anyMatch(a -> a.getBalance() == 14.0));
    }

    @Test
    void testTransferInvalidRequest() {
        var request = postJson("/transfer", new TransferMoneyRequest());
        assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(request, TransferMoneyResponse.class).body();
        });
    }

    <T> HttpRequest<T> postJson(String path, T request) {
        return HttpRequest.POST(URI.create(path), request)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);
    }

}