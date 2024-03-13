package ru.itis.kpfu.selyantsev.utils.mappers;

import org.mapstruct.Mapper;
import ru.itis.kpfu.selyantsev.dto.response.PhoneNumberResponse;
import ru.itis.kpfu.selyantsev.model.PhoneNumber;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {

    List<PhoneNumberResponse> toUserPhoneNumbersResponse(List<PhoneNumber> phoneNumbers);

    static PhoneNumberResponse getPhoneNumberResponse(PhoneNumber phoneNumber) {
        return PhoneNumberResponse.builder()
                .phoneNumberId(phoneNumber.getUserPhoneNumberId())
                .number(phoneNumber.getNumber())
                .activityStatus(phoneNumber.getActivityStatus())
                .userId(phoneNumber.getUser().getUserId())
                .build();
    }
}
