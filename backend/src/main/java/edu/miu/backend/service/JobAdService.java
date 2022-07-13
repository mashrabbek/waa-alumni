package edu.miu.backend.service;

import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.dto.responseDto.JobAdResponseAppliedDto;
import edu.miu.backend.dto.responseDto.JobAdResponseDto;

import java.io.IOException;
import java.util.List;

public interface JobAdService {
    List<JobAdResponseDto> findAll();

    JobAdDto findById(int id);


    JobAdResponseDto save(JobAdDto jobAdDto) throws IOException;

    void delete(int id);

    JobAdDto update(JobAdDto jobAdDto, int id) throws Exception;

    List<JobAdResponseDto> findByUsername(String username);

    List<JobAdResponseDto> findAllByUsernameExcluded(String username);

    List<JobAdResponseAppliedDto> findByIdApplied(int id);

    List<JobAdResponseDto> findTopAll();

    List<JobAdResponseDto> findRecentAll();
}
