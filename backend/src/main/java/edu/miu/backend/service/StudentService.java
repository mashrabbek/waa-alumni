package edu.miu.backend.service;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findByID(int id);

    Student save(Student student);

    void delete(int id);

    Student update(StudentDto student, int id) throws Exception;
}
