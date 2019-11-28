package ru.ignatov.bank.exceptions;


public class BusinessLogicException extends RuntimeException {
    private String businessErrorMessage;

    public BusinessLogicException(String message) {
        super(message);
        businessErrorMessage = message;
    }

    public String getBusinessErrorMessage() {
        return businessErrorMessage;
    }
}
