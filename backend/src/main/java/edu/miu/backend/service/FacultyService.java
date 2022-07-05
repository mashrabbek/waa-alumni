package edu.miu.backend.service;

import edu.miu.backend.entity.Faculty;

import java.util.List;

public interface FacultyService {
    List<Faculty> findAll();

    Faculty findById(int id);

    Faculty save(Faculty faculty);
}
