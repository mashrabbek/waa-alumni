package edu.miu.backend.repo;

import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface JobHistoryRepo extends CrudRepository<JobHistory, Integer> {
    List<JobHistory> findByStudent(Student student);
}
