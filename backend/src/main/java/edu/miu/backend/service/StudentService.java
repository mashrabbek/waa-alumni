package edu.miu.backend.service;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.dto.StudentResponseDto;
import edu.miu.backend.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(int id);

    StudentDto save(StudentDto studentDto);

    void delete(int id);

    StudentResponseDto update(StudentDto studentDto, int id) throws Exception;
}
