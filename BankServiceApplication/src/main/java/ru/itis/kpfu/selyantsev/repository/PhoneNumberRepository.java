package ru.itis.kpfu.selyantsev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.kpfu.selyantsev.model.PhoneNumber;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, UUID> {

    @Query("SELECT p FROM t_phone_number p WHERE p.user.userId = :userId AND p.activityStatus = :activityStatus")
    List<PhoneNumber> findAllByUserIdAAndActivityStatus(
            @Param("userId") UUID userId, @Param("activityStatus")ActivityStatus activityStatus
    );
}
