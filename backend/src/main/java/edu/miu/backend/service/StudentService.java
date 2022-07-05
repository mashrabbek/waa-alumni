package edu.miu.backend.service;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(int id);

    Student save(Student student);

    void delete(int id);

    StudentDto update(StudentDto studentDto, int id) throws Exception;
}
