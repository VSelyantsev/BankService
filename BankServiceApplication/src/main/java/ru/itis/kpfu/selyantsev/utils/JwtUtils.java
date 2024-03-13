package ru.itis.kpfu.selyantsev.utils;

import io.jsonwebtoken.Claims;
import ru.itis.kpfu.selyantsev.filter.JwtAuthentication;

import java.util.UUID;

public class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setAuthenticatedUserId(UUID.fromString(claims.get("userId", String.class)));
        jwtAuthentication.setEmail(claims.get("sub", String.class));
        jwtAuthentication.setFullName(claims.get("fullName", String.class));
        return jwtAuthentication;
    }
}
