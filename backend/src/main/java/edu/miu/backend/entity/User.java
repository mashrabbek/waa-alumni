package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Where(clause = "deleted = false")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique=true)
    private String username;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoggedIn;
    private Boolean active = Boolean.TRUE;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Address address;

    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    private List<JobAd> jobAds;
    private Boolean deleted = Boolean.FALSE;

}
