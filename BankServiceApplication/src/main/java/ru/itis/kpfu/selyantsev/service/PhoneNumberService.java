package ru.itis.kpfu.selyantsev.service;

import ru.itis.kpfu.selyantsev.dto.response.PhoneNumberResponse;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.List;
import java.util.UUID;

public interface PhoneNumberService {

    List<PhoneNumberResponse> findAllByUserIdAndActivityStatus(UUID userId, ActivityStatus activityStatus);
}
