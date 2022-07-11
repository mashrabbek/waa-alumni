package edu.miu.backend.service;

import edu.miu.backend.controller.FacultyController;
import edu.miu.backend.dto.FacultyDto;
import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.entity.Faculty;

import java.util.List;

public interface FacultyService {
    List<FacultyDto> findAll();

    Faculty findById(int id);

    FacultyDto save(FacultyDto facultyDto) throws Exception;

    FacultyDto update(FacultyDto facultyDto, String username)  throws Exception;

    void delete(int id);

    FacultyDto findByUsername(String username);

    void deleteByUsername(String username);
}
