package ru.itis.kpfu.selyantsev.service;

import ru.itis.kpfu.selyantsev.dto.response.BankAccountResponse;

public interface TransferService {

    BankAccountResponse transferMoney(Double amount, String userPhoneNumber);
}
