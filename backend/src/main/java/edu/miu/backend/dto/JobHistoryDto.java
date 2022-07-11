package edu.miu.backend.dto;


import edu.miu.backend.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Integer> tags;
    private Student student;

    private Boolean deleted = Boolean.FALSE;
}
