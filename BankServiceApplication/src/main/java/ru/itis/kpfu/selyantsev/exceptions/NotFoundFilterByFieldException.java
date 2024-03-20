package ru.itis.kpfu.selyantsev.exceptions;

public class NotFoundFilterByFieldException extends NotFoundException{
    public NotFoundFilterByFieldException(String orderField) {
        super(String.format("Can not filter by selected Field: %s", orderField));
    }
}
