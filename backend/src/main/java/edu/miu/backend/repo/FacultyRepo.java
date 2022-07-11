package edu.miu.backend.repo;

import edu.miu.backend.dto.FacultyDto;
import edu.miu.backend.entity.Faculty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepo extends CrudRepository<Faculty, Integer> {
    Optional<Faculty> findByUsername(String username);
    void deleteByUsername(String username);
}
