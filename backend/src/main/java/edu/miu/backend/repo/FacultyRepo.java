package edu.miu.backend.repo;

import edu.miu.backend.entity.Faculty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepo extends CrudRepository<Faculty, Integer> {
}
