package edu.miu.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private JobAd jobAds;

    @OneToOne
    private Student student;

    @ElementCollection
    private List<String> files;

}
