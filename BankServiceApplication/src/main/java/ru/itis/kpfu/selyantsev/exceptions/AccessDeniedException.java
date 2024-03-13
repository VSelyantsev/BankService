package ru.itis.kpfu.selyantsev.exceptions;

import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

public class AccessDeniedException extends org.springframework.security.access.AccessDeniedException {
    public AccessDeniedException(ActivityStatus activityStatus) {
        super(String.format("Permission to this resource is DENIED, your availability status is %s", activityStatus));
    }
}
