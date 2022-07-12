package edu.miu.backend.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.miu.backend.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobHistoryResponseDto {
    private Integer id;
    private String companyName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String reasonToLeave;
    private List<Tag> tags;
}
