package edu.miu.backend.service;

import edu.miu.backend.dto.JobHistoryDto;
import edu.miu.backend.dto.responseDto.JobHistoryResponseDto;
import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.repo.JobHistoryRepo;

import java.util.List;

public interface JobHistoryService {
    List<JobHistoryResponseDto> findAll();

    JobHistoryDto findById(int id);

    void deleteByID(int id);

    JobHistoryDto update(JobHistoryDto jobHistoryDto, String username);

    JobHistoryResponseDto save(JobHistoryDto jobHistoryDto, String username);

    List<JobHistoryResponseDto> findByUsername(String username);
}
