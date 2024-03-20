package ru.itis.kpfu.selyantsev.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;
import ru.itis.kpfu.selyantsev.exceptions.ParseDateFormatException;

import java.util.Optional;

@RequestMapping("/api/v1/search")
public interface SearchApi {

    @Operation(summary = "Search any User by few filters, can combine them")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Page<UserResponse>> searchUsersByFilters(
            @Parameter(
                    name = "Birth date",
                    description = "If entered filter Birth Date then output will be greater than entered BD"
            )  @RequestParam(required = false) Optional<String> birthDate,
            @Parameter(
                    name = "User Phone Number",
                    description = "If entered filter PHONE NUMBER then output by 100% similarity"
            ) @RequestParam(required = false) Optional<String> phoneNumber,
            @Parameter(
                    name = "User Full Name",
                    description = "If entered filter FullName then output by LIKE"
            ) @RequestParam(required = false) Optional<String> fullName,
            @Parameter(
                    name = "User Email",
                    description = "If entered filter EMAIL then output by 100% similarity"
            ) @RequestParam(required = false) Optional<String> email,
            @RequestParam(required = false) Optional<String> order,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws ParseDateFormatException;
}
