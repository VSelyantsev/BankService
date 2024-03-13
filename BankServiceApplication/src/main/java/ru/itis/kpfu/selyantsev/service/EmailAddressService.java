package ru.itis.kpfu.selyantsev.service;

import ru.itis.kpfu.selyantsev.dto.response.EmailAddressResponse;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.List;
import java.util.UUID;

public interface EmailAddressService {

    List<EmailAddressResponse> findAllByUserIdAndActivityStatus(UUID userId, ActivityStatus activityStatus);
}
