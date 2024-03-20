package ru.itis.kpfu.selyantsev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.kpfu.selyantsev.api.SearchApi;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;
import ru.itis.kpfu.selyantsev.exceptions.ParseDateFormatException;
import ru.itis.kpfu.selyantsev.service.SearchService;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SearchController implements SearchApi {

    private final SearchService searchService;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserResponse>> searchUsersByFilters(
            Optional<String > birthDate,
            Optional<String> phoneNumber,
            Optional<String> fullName,
            Optional<String> email,
            Optional<String> order,
            int page, int size
    ) throws ParseDateFormatException {
        Page<UserResponse> userResponsePage = searchService.searchUsersByFilters(
                birthDate, phoneNumber, fullName, email, order, page, size
        );

        return ResponseEntity.ok(userResponsePage);
    }
}
