package ru.itis.kpfu.selyantsev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.kpfu.selyantsev.api.UserApi;
import ru.itis.kpfu.selyantsev.dto.request.*;
import ru.itis.kpfu.selyantsev.dto.response.EmailAddressResponse;
import ru.itis.kpfu.selyantsev.dto.response.PhoneNumberResponse;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;
import ru.itis.kpfu.selyantsev.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public UserResponse create(UserRequest userRequest) {
        return userService.create(userRequest);
    }

    @Override
    public UserResponse addPhoneNumber(PhoneNumberRequest phoneNumber) {
        return userService.addPhoneNumber(phoneNumber);
    }

    @Override
    public UserResponse editPhoneNumber(EditPhoneNumberRequest editPhoneNumberRequest) {
        return userService.editPhoneNumber(editPhoneNumberRequest);
    }

    @Override
    public UserResponse addEmail(EmailAddressRequest email) {
        return userService.addEmail(email);
    }

    @Override
    public UserResponse editEmail(EditEmailRequest editEmailRequest) {
        return userService.editEmail(editEmailRequest);
    }

    @Override
    @ResponseBody
    public List<PhoneNumberResponse> deletePhoneNumber(PhoneNumberRequest phoneNumberRequest) {
        return userService.deletePhoneNumber(phoneNumberRequest);
    }

    @Override
    public List<EmailAddressResponse> deleteEmail(EmailAddressRequest emailAddressRequest) {
        return userService.deleteEmail(emailAddressRequest);
    }
}
