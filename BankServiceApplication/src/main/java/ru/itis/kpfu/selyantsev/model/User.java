package ru.itis.kpfu.selyantsev.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import ru.itis.kpfu.selyantsev.model.enums.ActivityStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "t_user")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID userId;
    private String fullName;
    private String hashPassword;

    @Enumerated(value = EnumType.STRING)
    private ActivityStatus activityStatus;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthDate;

    // think about CascadeType
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;

    // think about CascadeType
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EmailAddress> emailAddresses;

    // think about CascadeType
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BankAccount bankAccount;
}
