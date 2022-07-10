package edu.miu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private Integer id;
    private String email;
    private String lastName;
    private String firstName;
    private String password;
    private AddressDto address;
    private Integer majorId;
    private Float gpa;
    private String cv;
}
