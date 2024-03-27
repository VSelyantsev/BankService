package ru.itis.kpfu.selyantsev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.kpfu.selyantsev.model.BankAccount;

import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    BankAccount findBankAccountByUserUserId(UUID userId);
}
