package edu.miu.backend.dto;

import edu.miu.backend.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobAdResponseDto {
    private Integer id;
    private String description;
    private String benefits;
    private Integer creatorId;
    private List<Tag> tags;
    private List<String> files;
}
