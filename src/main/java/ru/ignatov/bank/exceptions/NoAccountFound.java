package ru.ignatov.bank.exceptions;

public class NoAccountFound extends BusinessLogicException {
    public NoAccountFound(int userId) {
        super("No user with userId: " + userId + " found");
    }
}
