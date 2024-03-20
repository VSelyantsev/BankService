package ru.itis.kpfu.selyantsev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.kpfu.selyantsev.exceptions.ParseDateFormatException;
import ru.itis.kpfu.selyantsev.model.User;

import java.util.Optional;

public interface CriteriaBuilderSearchRepository {
    Page<User> searchUsersByFilters(
            Optional<String > dateBirth,
            Optional<String> phoneNumber,
            Optional<String> fullName,
            Optional<String> email,
            Optional<String> orderBy,
            Pageable pageable
    ) throws ParseDateFormatException;
}
