package edu.miu.backend.dto;

import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    String username;
    private AddressDto address;
    private Integer majorId;
    private Float gpa;
    private MultipartFile file;
}
