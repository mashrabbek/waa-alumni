package edu.miu.backend.service;

import edu.miu.backend.controller.FacultyController;
import edu.miu.backend.dto.FacultyDto;
import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.entity.Faculty;

import java.util.List;

public interface FacultyService {
    List<Faculty> findAll();

    Faculty findById(int id);

    Faculty save(Faculty faculty);

    FacultyDto update(FacultyDto facultyDto, int id)  throws Exception;

    void delete(int id);
}
