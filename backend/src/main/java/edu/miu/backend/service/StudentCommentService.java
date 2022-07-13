package edu.miu.backend.service;

import edu.miu.backend.dto.StudentCommentDto;

import java.util.List;

public interface StudentCommentService {
    List<StudentCommentDto> findAll();

    StudentCommentDto findById(int id);

    StudentCommentDto save(StudentCommentDto studentCommentDto);

    void delete(int id);

    StudentCommentDto update(StudentCommentDto studentCommentDto, int id) throws Exception;

    List<StudentCommentDto> findByUsername(String username);
}
