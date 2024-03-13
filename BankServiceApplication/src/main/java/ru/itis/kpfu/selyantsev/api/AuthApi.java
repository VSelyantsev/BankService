package ru.itis.kpfu.selyantsev.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.kpfu.selyantsev.dto.jwt.JwtRequest;
import ru.itis.kpfu.selyantsev.dto.jwt.JwtResponse;
import ru.itis.kpfu.selyantsev.dto.jwt.RefreshJwtRequest;

@RequestMapping(value = "api/auth")
@Tag(name = "Authentication", description = "Authentication Operations")
public interface AuthApi {

    @Operation(summary = "Login with JWT")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/login"
    )
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<JwtResponse> login(
            @Parameter(name = "Jwt Request object", required = true)
            @NonNull @RequestBody JwtRequest jwtRequest
    );

    @Operation(summary = "Get NEW Access Token")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/token"
    )
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<JwtResponse> getNewAccessToken(
            @Parameter(name = "Refresh Token Request Object", required = true)
            @NonNull @RequestBody RefreshJwtRequest refreshJwtRequest
    );

    @Operation(summary = "Get NEW Refresh Token")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/refresh"
    )
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<JwtResponse> getNewRefreshToken(
            @Parameter(name = "Refresh token Request Object", required = true)
            @NonNull @RequestBody RefreshJwtRequest refreshJwtRequest
    );
}
