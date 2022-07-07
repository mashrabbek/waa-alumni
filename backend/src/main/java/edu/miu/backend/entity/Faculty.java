package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Faculty extends User{
    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "faculty")
    @JsonBackReference
    List<StudentComment> studentComments;
}
