package edu.miu.backend.entity;

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
    private Faculty faculty;
    @OneToOne
    private Student student;
}
