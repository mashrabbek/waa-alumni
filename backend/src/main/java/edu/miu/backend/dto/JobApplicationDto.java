package edu.miu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDto {
    private Integer id;
    private Integer jobAdId;
    private String studentUsername;
}
