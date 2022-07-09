package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
@SQLDelete(sql = "UPDATE student_comment SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
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

    private Boolean deleted = Boolean.FALSE;
}
