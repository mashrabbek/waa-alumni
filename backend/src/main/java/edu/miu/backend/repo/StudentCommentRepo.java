package edu.miu.backend.repo;

import edu.miu.backend.dto.StudentCommentDto;
import edu.miu.backend.entity.Student;
import edu.miu.backend.entity.StudentComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCommentRepo extends CrudRepository<StudentComment, Integer> {
    @Query("select sc from StudentComment sc where sc.student = ?1")
    List<StudentComment> findByStudent(Student student);
}
