package edu.miu.backend.service;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.dto.responseDto.StudentResponseDto;
import edu.miu.backend.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    StudentResponseDto findByUsername(String username);
    List<StudentResponseDto> findAll();

    Student findById(int id);

    StudentResponseDto save(StudentDto studentDto) throws SQLException;

    void delete(int id);

//    StudentResponseDto update(StudentDto studentDto, int id) throws Exception;

    StudentResponseDto update(StudentDto studentDto, String username) throws Exception;

    void deleteByUsername(String username);
}
