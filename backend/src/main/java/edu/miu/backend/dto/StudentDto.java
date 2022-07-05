package edu.miu.backend.dto;

import edu.miu.backend.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String email;
    private String lastName;
    private String firstName;
    private AddressDto address;
    private Integer majorId;
    private Float gpa;

}
