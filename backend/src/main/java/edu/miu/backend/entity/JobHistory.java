package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE job_history SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class JobHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String companyName;
    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
    private String reasonToLeave;

    @ManyToMany
    List<Tag> tags;

    @ManyToOne
    @JsonManagedReference
    private Student student;

    private Boolean deleted = Boolean.FALSE;

}
