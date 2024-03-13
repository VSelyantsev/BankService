package ru.itis.kpfu.selyantsev.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.kpfu.selyantsev.api.AuthApi;
import ru.itis.kpfu.selyantsev.dto.jwt.JwtRequest;
import ru.itis.kpfu.selyantsev.dto.jwt.JwtResponse;
import ru.itis.kpfu.selyantsev.dto.jwt.RefreshJwtRequest;
import ru.itis.kpfu.selyantsev.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<JwtResponse> login(@NonNull JwtRequest jwtRequest) {
        final JwtResponse token = authService.login(jwtRequest);
        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<JwtResponse> getNewAccessToken(@NonNull RefreshJwtRequest refreshJwtRequest) {
        final JwtResponse token = authService.getAccessToken(refreshJwtRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<JwtResponse> getNewRefreshToken(@NonNull RefreshJwtRequest refreshJwtRequest) {
        final JwtResponse token = authService.refresh(refreshJwtRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
