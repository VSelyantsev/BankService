package ru.itis.kpfu.selyantsev.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneNumberRequest {
    private String phoneNumber;
}
