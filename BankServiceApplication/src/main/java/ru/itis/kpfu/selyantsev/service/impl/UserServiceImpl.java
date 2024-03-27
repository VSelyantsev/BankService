package ru.itis.kpfu.selyantsev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.selyantsev.dto.request.*;
import ru.itis.kpfu.selyantsev.dto.response.EmailAddressResponse;
import ru.itis.kpfu.selyantsev.dto.response.PhoneNumberResponse;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;
import ru.itis.kpfu.selyantsev.exceptions.NotFoundEmailAddressException;
import ru.itis.kpfu.selyantsev.exceptions.NotFoundEmailException;
import ru.itis.kpfu.selyantsev.exceptions.NotFoundKeyException;
import ru.itis.kpfu.selyantsev.exceptions.NotFoundPhoneNumberException;
import ru.itis.kpfu.selyantsev.model.BankAccount;
import ru.itis.kpfu.selyantsev.model.EmailAddress;
import ru.itis.kpfu.selyantsev.model.PhoneNumber;
import ru.itis.kpfu.selyantsev.model.User;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;
import ru.itis.kpfu.selyantsev.repository.UserRepository;
import ru.itis.kpfu.selyantsev.service.EmailAddressService;
import ru.itis.kpfu.selyantsev.service.PhoneNumberService;
import ru.itis.kpfu.selyantsev.service.UserService;
import ru.itis.kpfu.selyantsev.utils.mappers.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PhoneNumberService phoneNumberService;
    private final EmailAddressService emailAddressService;


    @Override
    public UserResponse create(UserRequest userRequest) {
        User entity = userMapper.toEntity(userRequest);

        EmailAddress userEmailAddress = EmailAddress.builder()
                .user(entity)
                .email(userRequest.getEmail())
                .activityStatus(ActivityStatus.AVAILABLE)
                .build();

        String cutRequestPhoneNumber = subPhoneNumber(userRequest.getPhoneNumber());
        PhoneNumber userPhoneNumber = PhoneNumber.builder()
                .user(entity)
                .number(cutRequestPhoneNumber)
                .activityStatus(ActivityStatus.AVAILABLE)
                .build();

        entity.getEmailAddresses().add(userEmailAddress);
        entity.getPhoneNumbers().add(userPhoneNumber);

        BankAccount userBankAccount = BankAccount.builder()
                .user(entity)
                .initialDeposit(userRequest.getAmount())
                .amount(userRequest.getAmount())
                .build();
        entity.setBankAccount(userBankAccount);

        userRepository.save(entity);
        return userMapper.toResponse(entity);
    }

    // in controller add @PreAuthorize(isAuthenticated)
    @Override
    public UserResponse addPhoneNumber(PhoneNumberRequest phoneNumber) {
        User currentAuthenticatedUser = getCurrentAuthenticationUser()
                .map(this::getExistingUser)
                .orElseThrow(() -> new ru.itis.kpfu.selyantsev.exceptions.AccessDeniedException(ActivityStatus.ANONYMOUS));

        String cutRequestPhoneNumber = subPhoneNumber(phoneNumber.getPhoneNumber());
        PhoneNumber newPhoneNumber = PhoneNumber.builder()
                .number(cutRequestPhoneNumber)
                .activityStatus(ActivityStatus.AVAILABLE)
                .user(currentAuthenticatedUser)
                .build();

        currentAuthenticatedUser.getPhoneNumbers().add(newPhoneNumber);
        userRepository.save(currentAuthenticatedUser);

        return userMapper.toResponse(currentAuthenticatedUser);
    }

    @Override
    public UserResponse editPhoneNumber(EditPhoneNumberRequest editPhoneNumberRequest) {
        User currentAuthenticatedUser = getCurrentAuthenticationUser()
                .map(this::getExistingUser)
                .orElseThrow(() -> new ru.itis.kpfu.selyantsev.exceptions.AccessDeniedException(ActivityStatus.ANONYMOUS));

        String cutSourcePhoneNumber = subPhoneNumber(editPhoneNumberRequest.getSourcePhoneNumber());
        Optional<PhoneNumber> existingPhoneNumber = currentAuthenticatedUser.getPhoneNumbers()
                .stream()
                .filter(phoneNumber -> phoneNumber.getNumber().equals(cutSourcePhoneNumber))
                .findFirst();

        if (!existingPhoneNumber.isPresent()) {
            throw new NotFoundPhoneNumberException(editPhoneNumberRequest.getSourcePhoneNumber());
        }

        String cutTargetPhoneNumber = subPhoneNumber(editPhoneNumberRequest.getTargetPhoneNumber());
        existingPhoneNumber.ifPresent(phoneNumber -> phoneNumber.setNumber(cutTargetPhoneNumber));
        userRepository.save(currentAuthenticatedUser);

        return userMapper.toResponse(currentAuthenticatedUser);
    }

    @Override
    public UserResponse addEmail(EmailAddressRequest emailAddressRequest) {
        User currentAuthenticatedUser = getCurrentAuthenticationUser()
                .map(this::getExistingUser)
                .orElseThrow();

        EmailAddress newEmailAddress = EmailAddress.builder()
                .email(emailAddressRequest.getEmailAddress())
                .activityStatus(ActivityStatus.AVAILABLE)
                .user(currentAuthenticatedUser)
                .build();

        currentAuthenticatedUser.getEmailAddresses().add(newEmailAddress);
        userRepository.save(currentAuthenticatedUser);

        return userMapper.toResponse(currentAuthenticatedUser); 
    }

    @Override
    public UserResponse editEmail(EditEmailRequest editEmailRequest) {
        User currentAuthenticatedUser = getCurrentAuthenticationUser()
                .map(this::getExistingUser)
                .orElseThrow(() -> new ru.itis.kpfu.selyantsev.exceptions.AccessDeniedException(ActivityStatus.ANONYMOUS));

        Optional<EmailAddress> existingEmailAddress = currentAuthenticatedUser.getEmailAddresses()
                .stream()
                .filter(emailAddress -> emailAddress.getEmail().equals(editEmailRequest.getSourceEmail()))
                .findFirst();

        if (!existingEmailAddress.isPresent()) {
            throw new NotFoundEmailAddressException(editEmailRequest.getSourceEmail());
        }

        existingEmailAddress.ifPresent(emailAddress -> emailAddress.setEmail(editEmailRequest.getTargetEmail()));
        userRepository.save(currentAuthenticatedUser);

        return userMapper.toResponse(currentAuthenticatedUser);
    }

    @Override
    public List<PhoneNumberResponse> deletePhoneNumber(PhoneNumberRequest phoneNumberRequest) {
        User authenticatedUser = getCurrentAuthenticationUser()
                .map(this::getExistingUser)
                .orElseThrow(() -> new ru.itis.kpfu.selyantsev.exceptions.AccessDeniedException(ActivityStatus.ANONYMOUS));

        Optional<PhoneNumber> existingPhoneNumber = authenticatedUser.getPhoneNumbers()
                .stream()
                .filter(phoneNumber -> phoneNumber.getNumber().equals(phoneNumberRequest.getPhoneNumber()))
                .findFirst();

        if (!existingPhoneNumber.isPresent()) {
            throw new NotFoundPhoneNumberException(phoneNumberRequest.getPhoneNumber());
        }

        existingPhoneNumber.ifPresent(phoneNumber -> phoneNumber.setActivityStatus(ActivityStatus.NOT_AVAILABLE));
        userRepository.save(authenticatedUser);

        return phoneNumberService.findAllByUserIdAndActivityStatus(authenticatedUser.getUserId(), ActivityStatus.AVAILABLE);
    }

    @Override
    public List<EmailAddressResponse> deleteEmail(EmailAddressRequest emailAddressRequest) {
        User authenticatedUser = getCurrentAuthenticationUser()
                .map(this::getExistingUser)
                .orElseThrow(() -> new ru.itis.kpfu.selyantsev.exceptions.AccessDeniedException(ActivityStatus.ANONYMOUS));

        Optional<EmailAddress> existingEmailAddress = authenticatedUser.getEmailAddresses()
                .stream()
                .filter(emailAddress -> emailAddress.getEmail().equals(emailAddressRequest.getEmailAddress()))
                .findFirst();

        if (!existingEmailAddress.isPresent()) {
            throw new NotFoundEmailAddressException(emailAddressRequest.getEmailAddress());
        }

        existingEmailAddress.ifPresent(emailAddress -> emailAddress.setActivityStatus(ActivityStatus.NOT_AVAILABLE));
        userRepository.save(authenticatedUser);

        return emailAddressService.findAllByUserIdAndActivityStatus(authenticatedUser.getUserId(), ActivityStatus.AVAILABLE);
    }

    @Override
    public Optional<UserResponse> getCurrentAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException(ActivityStatus.ANONYMOUS.name());
        }

        String existingEmail = authentication.getName();
        User authenticatedUser = userRepository.findUserByEmailAddresses(existingEmail)
                .orElseThrow(() -> new NotFoundEmailException(existingEmail));

        if (!authenticatedUser.getActivityStatus().name().equals(ActivityStatus.AVAILABLE.name())) {
            throw new AccessDeniedException(ActivityStatus.NOT_AVAILABLE.name());
        }

        return Optional.of(userMapper.toResponse(authenticatedUser));
    }

    @Override
    public Optional<UserResponse> findUserByPhoneNumber(String userPhoneNumber) {
        String cutPhoneNumber = subPhoneNumber(userPhoneNumber);

        User foundedUser = userRepository.findUserByPhoneNumber(cutPhoneNumber)
                .orElseThrow(() -> new NotFoundPhoneNumberException(cutPhoneNumber));

        return Optional.of(userMapper.toResponse(foundedUser));
    }

    private User getExistingUser(UserResponse userResponse) {
        return userRepository.findById(userResponse.getUserId())
                .orElseThrow(() -> new NotFoundKeyException(userResponse.getUserId()));
    }

    private String subPhoneNumber(String phoneNumber) {
        return phoneNumber.replace("+", "").trim();
    }
}
