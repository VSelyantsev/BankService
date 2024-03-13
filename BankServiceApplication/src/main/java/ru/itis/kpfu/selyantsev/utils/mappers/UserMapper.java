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

@Mapper(componentModel = "spring", imports = {PhoneNumberMapper.class, EmailAddressMapper.class})
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "hashPassword", expression = "java(UserMapper.getHashPassword(userRequest))")
    @Mapping(target = "phoneNumbers", expression = "java(UserMapper.initializePhoneNumbersList())")
    @Mapping(target = "emailAddresses", expression = "java(UserMapper.initializeEmailAddressesList())")
    @Mapping(target = "activityStatus", expression = "java(UserMapper.initializeActivityStatus())")
    @Mapping(target = "bankAccount", ignore = true)
    User toEntity(UserRequest userRequest);

    // write a UserResponse class
    UserResponse toResponse(User user);

    static String getHashPassword(UserRequest userRequest) {
        return new BCryptPasswordEncoder().encode(userRequest.getPassword());
    }

    static List<PhoneNumber> initializePhoneNumbersList() {
        return new ArrayList<>();
    }

    static List<EmailAddress> initializeEmailAddressesList() {
        return new ArrayList<>();
    }

    static ActivityStatus initializeActivityStatus() {
        return ActivityStatus.AVAILABLE;
    }
}
