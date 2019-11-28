package ru.ignatov.bank.exceptions;

public class UserAlreadyCreated extends BusinessLogicException {
    public UserAlreadyCreated(String message) {
        super(message);
    }
}
