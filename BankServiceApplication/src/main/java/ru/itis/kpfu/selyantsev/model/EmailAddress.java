package ru.itis.kpfu.selyantsev.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "t_email_address")
public class EmailAddress {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID userEmailId;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private ActivityStatus activityStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
}
