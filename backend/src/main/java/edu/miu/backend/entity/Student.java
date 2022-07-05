package edu.miu.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
public class Student extends User {

    @ManyToOne
    private Department major;
    private Float gpa;

    @OneToMany(mappedBy = "student")
    List<JobHistory> jobHistories;

}
