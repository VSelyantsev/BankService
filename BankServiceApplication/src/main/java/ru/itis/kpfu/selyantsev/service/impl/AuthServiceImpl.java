package ru.itis.kpfu.selyantsev.service.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.selyantsev.dto.jwt.JwtRequest;
import ru.itis.kpfu.selyantsev.dto.jwt.JwtResponse;
import ru.itis.kpfu.selyantsev.exceptions.AuthException;
import ru.itis.kpfu.selyantsev.exceptions.NotFoundEmailException;
import ru.itis.kpfu.selyantsev.filter.JwtProvider;
import ru.itis.kpfu.selyantsev.model.User;
import ru.itis.kpfu.selyantsev.repository.UserRepository;
import ru.itis.kpfu.selyantsev.service.AuthService;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder encoder;

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        final User existingUser = userRepository.findUserByEmailAddresses(jwtRequest.getEmail())
                .orElseThrow(() -> new NotFoundEmailException(jwtRequest.getEmail()));
        if (encoder.matches(jwtRequest.getPassword(), existingUser.getHashPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(existingUser);
            final String refreshToken = jwtProvider.generateRefreshToken(existingUser);
            String userEmail = existingUser.getEmailAddresses().get(0).getEmail();
            refreshStorage.put(userEmail, refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Incorrect Password");
        }
    }

    @Override
    public JwtResponse getAccessToken(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getAccesClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User existingUser = userRepository.findUserByEmailAddresses(email)
                        .orElseThrow(() -> new NotFoundEmailException(email));
                final String accessToken = jwtProvider.generateAccessToken(existingUser);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User existingUser = userRepository.findUserByEmailAddresses(email)
                        .orElseThrow(() -> new NotFoundEmailException(email));
                final String accessToken = jwtProvider.generateAccessToken(existingUser);
                final String newRefreshToken = jwtProvider.generateRefreshToken(existingUser);
                String userEmail = existingUser.getEmailAddresses().get(0).getEmail();
                refreshStorage.put(userEmail, newRefreshToken);
                return new JwtResponse(accessToken, refreshToken);
            }
        }
        throw new AuthException("Invalid JWT token");
    }
}
