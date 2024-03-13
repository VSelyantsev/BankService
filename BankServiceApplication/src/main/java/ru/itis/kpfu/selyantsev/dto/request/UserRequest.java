package ru.itis.kpfu.selyantsev.dto.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String fullName;
    private String password;
    private Double amount;
    private String phoneNumber;
    private String email;
    private Date birthDate;
}
