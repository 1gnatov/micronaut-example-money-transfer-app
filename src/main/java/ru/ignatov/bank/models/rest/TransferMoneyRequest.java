package ru.ignatov.bank.models.rest;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Introspected
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyRequest {
    @NotNull
    private Integer userIdFrom;
    @NotNull
    private Integer userIdTo;
    @NotNull
    private BigDecimal amount;
}
