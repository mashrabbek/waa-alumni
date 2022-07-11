package edu.miu.backend.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import edu.miu.backend.entity.Student;
import edu.miu.backend.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobHistoryDto {

    private Integer id;
    private String companyName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reasonToLeave;

    List<Integer> tags;
    private Student student;

    private Boolean deleted = Boolean.FALSE;
}
