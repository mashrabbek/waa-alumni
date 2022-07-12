package edu.miu.backend.dto.responseDto;

import edu.miu.backend.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private String username;
    private AddressDto address;
    private Integer majorId;
    private Float gpa;
    private String cv;
}
