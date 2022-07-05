package edu.miu.backend.repo;

import edu.miu.backend.entity.JobHistory;
import org.springframework.data.repository.CrudRepository;

public interface JobHistoryRepo extends CrudRepository<JobHistory, Integer> {
}
