package edu.miu.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Student extends User {

    @ManyToOne(cascade = CascadeType.ALL)
    private Department major;
    private Float gpa;

    @OneToMany(mappedBy = "student")
    List<JobHistory> jobHistories;

}
