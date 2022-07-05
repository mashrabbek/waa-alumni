package edu.miu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDto {
    private String email;
    private String lastName;
    private String firstName;
    private String password;
    private AddressDto address;
    private Integer departmentId;
}
