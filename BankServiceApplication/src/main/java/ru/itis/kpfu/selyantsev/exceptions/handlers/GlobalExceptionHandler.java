package ru.itis.kpfu.selyantsev.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.kpfu.selyantsev.exceptions.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            NotFoundException.class, NotFoundKeyException.class, NotFoundPhoneNumberException.class,
            NotFoundEmailException.class, NotFoundEmailAddressException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ExceptionMessage handlerOnNotFoundExceptionMessages(Exception exception) {
        return getInfoAboutMessage(exception);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ExceptionMessage handlerOnAllException(Exception exception) {
        return getInfoAboutMessage(exception);
    }

    private ExceptionMessage getInfoAboutMessage(Exception exception) {
        return new ExceptionMessage(exception.getMessage(), exception.getClass().getName());
    }
}
