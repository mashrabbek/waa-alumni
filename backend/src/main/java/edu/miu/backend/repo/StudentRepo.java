package edu.miu.backend.repo;

import edu.miu.backend.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface StudentRepo extends CrudRepository<Student, Integer> {

    Optional<Student> findByUsername(String username);

    void deleteByUsername(String username);
}
