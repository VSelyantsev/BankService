package ru.itis.kpfu.selyantsev.dto.response;

import lombok.*;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private UUID userId;
    private String fullName;
    private ActivityStatus activityStatus;
    private Date birthDate;
    private List<String> phoneNumbers;
    private List<String> emailAddresses;
    private Double amount;
}
