package edu.miu.backend.repo;

import edu.miu.backend.entity.JobHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobHistoryRepo extends CrudRepository<JobHistory, Integer> {
}
