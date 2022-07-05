package edu.miu.backend.service.impl;

import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.repo.JobHistoryRepo;
import edu.miu.backend.service.JobHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepo jobHistoryRepo;


    @Override
    public List<JobHistory> findAll() {
        return (List<JobHistory>) jobHistoryRepo.findAll();
    }

    @Override
    public JobHistory findById(int id) {
        return jobHistoryRepo.findById(id).get();
    }
}
