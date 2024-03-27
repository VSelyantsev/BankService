package ru.itis.kpfu.selyantsev.service;

import ru.itis.kpfu.selyantsev.dto.request.*;
import ru.itis.kpfu.selyantsev.dto.response.EmailAddressResponse;
import ru.itis.kpfu.selyantsev.dto.response.PhoneNumberResponse;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponse create(UserRequest userRequest);

    UserResponse addPhoneNumber(PhoneNumberRequest phoneNumber);

    UserResponse editPhoneNumber(EditPhoneNumberRequest editPhoneNumberRequest);

    UserResponse addEmail(EmailAddressRequest emailAddressRequest);

    UserResponse editEmail(EditEmailRequest editEmailRequest);

    List<PhoneNumberResponse> deletePhoneNumber(PhoneNumberRequest phoneNumberRequest);

    List<EmailAddressResponse> deleteEmail(EmailAddressRequest emailAddressRequest);

    Optional<UserResponse> getCurrentAuthenticationUser();

    Optional<UserResponse> findUserByPhoneNumber(String userPhoneNumber);
}
