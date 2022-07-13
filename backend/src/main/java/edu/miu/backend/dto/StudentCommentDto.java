package edu.miu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCommentDto {
    private Integer id;
    private String studentUsername;
    private String facultyUsername;
    private String comment;
}
