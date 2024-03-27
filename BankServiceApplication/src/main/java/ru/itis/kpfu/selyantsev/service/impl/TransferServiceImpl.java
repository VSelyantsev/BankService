package ru.itis.kpfu.selyantsev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.selyantsev.dto.response.BankAccountResponse;
import ru.itis.kpfu.selyantsev.dto.response.UserResponse;
import ru.itis.kpfu.selyantsev.exceptions.AccessDeniedException;
import ru.itis.kpfu.selyantsev.exceptions.NotFoundPhoneNumberException;
import ru.itis.kpfu.selyantsev.model.BankAccount;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;
import ru.itis.kpfu.selyantsev.repository.BankAccountRepository;
import ru.itis.kpfu.selyantsev.service.TransferService;
import ru.itis.kpfu.selyantsev.service.UserService;
import ru.itis.kpfu.selyantsev.utils.mappers.BankAccountMapper;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final UserService userService;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Override
    public BankAccountResponse transferMoney(Double amount, String userPhoneNumber) {
        Optional<UserResponse> currentAuthenticationUser = userService.getCurrentAuthenticationUser();
        Optional<UserResponse> foundedUserResponse = userService.findUserByPhoneNumber(userPhoneNumber);

        if (currentAuthenticationUser.isEmpty()) {
            throw new AccessDeniedException(ActivityStatus.ANONYMOUS);
        }

        if (foundedUserResponse.isEmpty()) {
            throw new NotFoundPhoneNumberException(userPhoneNumber);
        }

        UUID userId = currentAuthenticationUser.get().getUserId();
        UUID foundedUserId = foundedUserResponse.get().getUserId();

        BankAccount currentUserBankAccount = bankAccountRepository.findBankAccountByUserUserId(userId);
        BankAccount foundedUserBankAccount = bankAccountRepository.findBankAccountByUserUserId(foundedUserId);

        double currentUserAmount = currentUserBankAccount.getAmount();
        double foundedUserAmount = foundedUserBankAccount.getAmount();

        synchronized (currentUserBankAccount) {
            synchronized (foundedUserBankAccount) {
                currentUserBankAccount.setAmount(currentUserAmount - amount);
                foundedUserBankAccount.setAmount(foundedUserAmount + amount);
            }
        }

        bankAccountRepository.save(foundedUserBankAccount);
        return bankAccountMapper.toResponse(bankAccountRepository.save(currentUserBankAccount));
    }

}
