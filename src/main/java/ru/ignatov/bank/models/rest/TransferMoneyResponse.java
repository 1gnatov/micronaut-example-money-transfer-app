package ru.ignatov.bank.models.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyResponse {
    private List<AccountResponse> accounts;
}
