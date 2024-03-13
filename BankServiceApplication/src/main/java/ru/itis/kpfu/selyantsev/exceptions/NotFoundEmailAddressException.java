package ru.itis.kpfu.selyantsev.exceptions;

public class NotFoundEmailAddressException extends NotFoundException{
    public NotFoundEmailAddressException(String emailAddress) {
        super(String.format("Entered email address NOT FOUND: %s", emailAddress));
    }
}
