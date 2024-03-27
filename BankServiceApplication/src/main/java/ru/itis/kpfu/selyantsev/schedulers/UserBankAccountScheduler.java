package ru.itis.kpfu.selyantsev.schedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.kpfu.selyantsev.model.BankAccount;
import ru.itis.kpfu.selyantsev.model.User;
import ru.itis.kpfu.selyantsev.repository.UserRepository;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class UserBankAccountScheduler {

    private final UserRepository userRepository;

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void updateUserAmount() {
        List<User> updatedUser = userRepository.findActiveUser()
                .stream()
                .peek(user -> {
                    BankAccount userBankAccount = user.getBankAccount();
                    double newAmount = userBankAccount.getAmount() * 1.05;
                    double maxAmount = userBankAccount.getInitialDeposit() * 2.07;

                    if (newAmount > maxAmount) {
                        userBankAccount.setAmount(maxAmount);
                    } else {
                        userBankAccount.setAmount(newAmount);
                    }
                }).toList();

        userRepository.saveAll(updatedUser);
    }
}
