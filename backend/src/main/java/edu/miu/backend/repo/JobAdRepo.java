package edu.miu.backend.repo;

import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.JobAd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAdRepo extends CrudRepository<JobAd, Integer> {
}