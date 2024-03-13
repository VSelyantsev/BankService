package ru.itis.kpfu.selyantsev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.selyantsev.dto.response.EmailAddressResponse;
import ru.itis.kpfu.selyantsev.model.EmailAddress;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;
import ru.itis.kpfu.selyantsev.repository.EmailAddressRepository;
import ru.itis.kpfu.selyantsev.service.EmailAddressService;
import ru.itis.kpfu.selyantsev.utils.mappers.EmailAddressMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailAddressServiceImpl implements EmailAddressService {

    private final EmailAddressRepository emailAddressRepository;
    private final EmailAddressMapper emailAddressMapper;

    @Override
    public List<EmailAddressResponse> findAllByUserIdAndActivityStatus(UUID userId, ActivityStatus activityStatus) {
        List<EmailAddress> emailAddresses = emailAddressRepository
                .findAllByUserIdAndActivityStatus(userId, activityStatus);
        return emailAddressMapper.toUserEmailAddressesResponse(emailAddresses);
    }
}
