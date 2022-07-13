package edu.miu.backend.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAdResponseAppliedDto {
    private String username;
    private String cv;
}
