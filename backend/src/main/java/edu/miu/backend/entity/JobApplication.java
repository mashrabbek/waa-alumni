package edu.miu.backend.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SQLDelete(sql = "UPDATE job_application SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
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

    private Boolean deleted = Boolean.FALSE;

}
