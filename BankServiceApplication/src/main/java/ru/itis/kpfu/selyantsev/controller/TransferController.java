package ru.itis.kpfu.selyantsev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.kpfu.selyantsev.api.TransferApi;
import ru.itis.kpfu.selyantsev.dto.response.BankAccountResponse;
import ru.itis.kpfu.selyantsev.service.TransferService;

@RestController
@RequiredArgsConstructor
public class TransferController implements TransferApi {

    private final TransferService transferService;

    @Override
    @PreAuthorize("isAuthenticated()")
    public BankAccountResponse transferMoney(Double amount, String userPhoneNumber) {
        return transferService.transferMoney(amount, userPhoneNumber);
    }
}
