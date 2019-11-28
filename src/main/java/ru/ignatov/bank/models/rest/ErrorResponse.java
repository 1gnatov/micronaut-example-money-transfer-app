package ru.ignatov.bank.models.rest;

import lombok.Data;

@Data
public class ErrorResponse {
    String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}
