package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class StudentComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;
    @ManyToOne
    @JsonManagedReference
    private Faculty faculty;
    @OneToOne
    private Student student;
}
