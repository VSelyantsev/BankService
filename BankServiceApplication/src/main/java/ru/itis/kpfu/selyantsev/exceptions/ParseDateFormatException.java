package ru.itis.kpfu.selyantsev.exceptions;

import java.text.ParseException;

public class ParseDateFormatException extends ParseException {
    public ParseDateFormatException(String incorrectDate, int errorOffset) {
        super(String.format("Entered Incorrect Date format: %s, position: %s. Use this: yyyy-MM-dd",
                                incorrectDate, errorOffset), errorOffset);

    }
}
