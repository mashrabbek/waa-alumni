package edu.miu.backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class JobHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String companyName;
    @NotNull
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reasonToLeave;

    @ManyToMany
    List<Tag> tags;

    @ManyToOne
    private Student student;

}