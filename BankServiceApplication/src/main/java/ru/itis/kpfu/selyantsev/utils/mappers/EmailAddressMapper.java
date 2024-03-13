package ru.itis.kpfu.selyantsev.utils.mappers;

import org.mapstruct.Mapper;
import ru.itis.kpfu.selyantsev.dto.response.EmailAddressResponse;
import ru.itis.kpfu.selyantsev.model.EmailAddress;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailAddressMapper {

    List<EmailAddressResponse> toUserEmailAddressesResponse(List<EmailAddress> emailAddresses);

    static EmailAddressResponse getEmailAddressResponse(EmailAddress emailAddress) {
        return EmailAddressResponse.builder()
                .emailUserId(emailAddress.getUserEmailId())
                .email(emailAddress.getEmail())
                .activityStatus(emailAddress.getActivityStatus())
                .userId(emailAddress.getUser().getUserId())
                .build();
    }
}
