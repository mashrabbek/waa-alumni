package edu.miu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobAdDto {
    private Integer id;
    private String description;
    private String benefits;
    private String ownerUsername;
    private List<Integer> tagIds;
    private MultipartFile[] files;
}