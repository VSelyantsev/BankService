package ru.itis.kpfu.selyantsev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.kpfu.selyantsev.model.EmailAddress;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmailAddressRepository extends JpaRepository<EmailAddress, UUID> {

    @Query("SELECT e FROM t_email_address e WHERE e.user.userId = :userId AND e.activityStatus = :activityStatus")
    List<EmailAddress> findAllByUserIdAndActivityStatus(
            @Param("userId") UUID userId , @Param("activityStatus") ActivityStatus activityStatus
    );
}
