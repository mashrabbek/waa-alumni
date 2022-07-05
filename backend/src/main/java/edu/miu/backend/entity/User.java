package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String password;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoggedIn;
    @Value("true")
    private Boolean active;
    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "creator")
    private List<JobAd> jobAds;
}
