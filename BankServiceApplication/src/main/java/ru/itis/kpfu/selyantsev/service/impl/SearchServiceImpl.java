package ru.itis.kpfu.selyantsev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;
import ru.itis.kpfu.selyantsev.exceptions.ParseDateFormatException;
import ru.itis.kpfu.selyantsev.repository.CriteriaBuilderSearchRepository;
import ru.itis.kpfu.selyantsev.service.SearchService;
import ru.itis.kpfu.selyantsev.utils.mappers.UserMapper;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final CriteriaBuilderSearchRepository searchRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserResponse> searchUsersByFilters(
            Optional<String> dateBirth,
            Optional<String> phoneNumber,
            Optional<String> fullName,
            Optional<String> email,
            Optional<String> order,
            int page, int size) throws ParseDateFormatException {
        Pageable pageable = PageRequest.of(page, size);
        return searchRepository.searchUsersByFilters(dateBirth, phoneNumber, fullName, email, order, pageable)
                .map(userMapper::toResponse);
    }
}
