package ru.itis.kpfu.selyantsev.dto.response;

import lombok.*;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailAddressResponse {
    private UUID emailUserId;
    private String email;
    private ActivityStatus activityStatus;
    private UUID userId;
}
