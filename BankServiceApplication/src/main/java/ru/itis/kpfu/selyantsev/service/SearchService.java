package ru.itis.kpfu.selyantsev.service;

import org.springframework.data.domain.Page;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;
import ru.itis.kpfu.selyantsev.exceptions.ParseDateFormatException;

import java.util.Date;
import java.util.Optional;

public interface SearchService {
    Page<UserResponse> searchUsersByFilters(
            Optional<String > dateBirth,
            Optional<String> phoneNumber,
            Optional<String> fullName,
            Optional<String> email,
            Optional<String> order,
            int page, int size
    ) throws ParseDateFormatException;
}
