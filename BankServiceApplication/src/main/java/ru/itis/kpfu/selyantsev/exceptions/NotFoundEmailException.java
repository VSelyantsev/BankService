package ru.itis.kpfu.selyantsev.exceptions;

public class NotFoundEmailException extends NotFoundException {
    public NotFoundEmailException(String message) {
        super(String.format("User with this email %s NOT FOUND", message));
    }
}
