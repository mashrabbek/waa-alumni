package edu.miu.backend.service;

import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.dto.responseDto.JobAdResponseDto;
import edu.miu.backend.entity.JobAd;

import java.io.IOException;
import java.util.List;

public interface JobAdService {
    List<JobAd> findAll();

    JobAdDto findById(int id);


    JobAdResponseDto save(JobAdDto jobAdDto) throws IOException;

    void delete(int id);

    JobAdDto update(JobAdDto jobAdDto, int id) throws Exception;

    List<JobAdResponseDto> findByUsername(String username);
}
