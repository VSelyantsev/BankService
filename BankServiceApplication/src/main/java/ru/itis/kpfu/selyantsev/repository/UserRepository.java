package ru.itis.kpfu.selyantsev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.kpfu.selyantsev.model.EmailAddress;
import ru.itis.kpfu.selyantsev.model.PhoneNumber;
import ru.itis.kpfu.selyantsev.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM t_user u JOIN u.emailAddresses e WHERE e.email = :email")
    Optional<User> findUserByEmailAddresses(@Param("email") String email);

    @Query("SELECT u FROM t_user u JOIN u.bankAccount b " +
            "WHERE u.activityStatus = 'AVAILABLE' AND b.amount < b.initialDeposit * 2.07")
    List<User> findActiveUser();

    @Query("SELECT u FROM t_user u JOIN u.phoneNumbers p WHERE p.number = :phoneNumber")
    Optional<User> findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
