package ru.itis.kpfu.selyantsev.exceptions;

public class NotFoundPhoneNumberException extends NotFoundException{
    public NotFoundPhoneNumberException(String phoneNumber) {
        super(String.format("Entered phone number NOT FOUND: %s", phoneNumber));
    }
}
