package ru.itis.kpfu.selyantsev.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponse {
    private UUID bankAccountId;
    private Double amount;
    private UUID userId;
}
