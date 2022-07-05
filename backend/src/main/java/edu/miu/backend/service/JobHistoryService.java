package edu.miu.backend.service;

import edu.miu.backend.entity.JobHistory;

import java.util.List;

public interface JobHistoryService {
    List<JobHistory> findAll();

    JobHistory findById(int id);
}
