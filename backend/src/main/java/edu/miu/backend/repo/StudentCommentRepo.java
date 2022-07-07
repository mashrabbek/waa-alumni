package edu.miu.backend.repo;

import edu.miu.backend.entity.StudentComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCommentRepo extends CrudRepository<StudentComment, Integer> {
}
