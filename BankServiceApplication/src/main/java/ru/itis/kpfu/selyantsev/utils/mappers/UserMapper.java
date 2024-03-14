package ru.itis.kpfu.selyantsev.utils.mappers;

import org.aspectj.weaver.ast.Literal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.kpfu.selyantsev.dto.request.UserRequest;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;
import ru.itis.kpfu.selyantsev.model.EmailAddress;
import ru.itis.kpfu.selyantsev.model.PhoneNumber;
import ru.itis.kpfu.selyantsev.model.User;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {PhoneNumberMapper.class, EmailAddressMapper.class})
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "hashPassword", expression = "java(UserMapper.getHashPassword(userRequest))")
    @Mapping(target = "phoneNumbers", expression = "java(UserMapper.initializePhoneNumberList())")
    @Mapping(target = "emailAddresses", expression = "java(UserMapper.initializeEmailAddressList())")
    @Mapping(target = "activityStatus", expression = "java(UserMapper.initializeActivityStatus())")
    @Mapping(target = "bankAccount", ignore = true)
    User toEntity(UserRequest userRequest);


    @Mapping(target = "phoneNumbers", expression = "java(UserMapper.getUserPhoneNumbers(user))")
    @Mapping(target = "emailAddresses", expression = "java(UserMapper.getUserEmailAddresses(user))")
    @Mapping(target = "amount", expression = "java(UserMapper.getUserBankAmount(user))")
    UserResponse toResponse(User user);

    static String getHashPassword(UserRequest userRequest) {
        return new BCryptPasswordEncoder().encode(userRequest.getPassword());
    }

    static List<String> getUserPhoneNumbers(User user) {
        return user.getPhoneNumbers().stream()
                .map(phoneNumber -> phoneNumber.getNumber())
                .collect(Collectors.toList());
    }

    static List<String> getUserEmailAddresses(User user) {
        return user.getEmailAddresses().stream()
                .map(emailAddress -> emailAddress.getEmail())
                .collect(Collectors.toList());
    }

    static Double getUserBankAmount(User user) {
        return user.getBankAccount().getAmount();
    }

    static List<PhoneNumber> initializePhoneNumberList() {
        return new ArrayList<>();
    }

    static List<EmailAddress> initializeEmailAddressList() {
        return new ArrayList<>();
    }

    static ActivityStatus initializeActivityStatus() {
        return ActivityStatus.AVAILABLE;
    }
}
