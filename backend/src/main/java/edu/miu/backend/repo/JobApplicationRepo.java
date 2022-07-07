package edu.miu.backend.repo;

import edu.miu.backend.entity.JobApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepo extends CrudRepository<JobApplication, Integer> {
}
