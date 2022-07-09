package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SQLDelete(sql = "UPDATE student SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Student extends User {

    @ManyToOne
    private Department major;
    private Float gpa;

    @OneToMany(mappedBy = "student")
    @JsonBackReference
    List<JobHistory> jobHistories;

    private Boolean deleted = Boolean.FALSE;

}
