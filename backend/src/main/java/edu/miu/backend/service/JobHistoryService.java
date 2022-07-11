package edu.miu.backend.service;

import edu.miu.backend.dto.JobHistoryDto;
import edu.miu.backend.entity.JobHistory;

import java.util.List;

public interface JobHistoryService {
    List<JobHistoryDto> findAll();

    JobHistoryDto findById(int id);

    JobHistoryDto update(JobHistoryDto jobHistoryDto, int id);

    void deleteByID(int id);
}
