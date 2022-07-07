package edu.miu.backend.service;

import edu.miu.backend.dto.JobApplicationDto;

import java.util.List;

public interface JobApplicationService {

    List<JobApplicationDto> findAll();

    JobApplicationDto findById(int id);

    JobApplicationDto save(JobApplicationDto jobApplicationDto);

    void delete(int id);

    JobApplicationDto update(JobApplicationDto jobApplicationDto, int id) throws Exception;
}
