package ru.itis.kpfu.selyantsev.dto.response;

import lombok.*;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneNumberResponse {
    private UUID phoneNumberId;
    private String number;
    private ActivityStatus activityStatus;
    private UUID userId;
}
