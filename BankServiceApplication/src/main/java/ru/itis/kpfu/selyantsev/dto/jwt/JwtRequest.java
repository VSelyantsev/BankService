package ru.itis.kpfu.selyantsev.dto.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
    private String email;
    private String password;
}
