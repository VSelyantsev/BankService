package ru.itis.kpfu.selyantsev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.selyantsev.dto.response.PhoneNumberResponse;
import ru.itis.kpfu.selyantsev.model.PhoneNumber;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;
import ru.itis.kpfu.selyantsev.repository.PhoneNumberRepository;
import ru.itis.kpfu.selyantsev.service.PhoneNumberService;
import ru.itis.kpfu.selyantsev.utils.mappers.PhoneNumberMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final PhoneNumberMapper phoneNumberMapper;

    @Override
    public List<PhoneNumberResponse> findAllByUserIdAndActivityStatus(UUID userId, ActivityStatus activityStatus) {
        List<PhoneNumber> phoneNumbers = phoneNumberRepository
                .findAllByUserIdAAndActivityStatus(userId, activityStatus);
        return phoneNumberMapper.toUserPhoneNumbersResponse(phoneNumbers);
    }
}
