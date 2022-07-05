package edu.miu.backend.service.impl;

import edu.miu.backend.entity.Faculty;
import edu.miu.backend.repo.FacultyRepo;
import edu.miu.backend.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepo facultyRepo;

    @Override
    public List<Faculty> findAll() {
        return (List<Faculty>) facultyRepo.findAll();
    }

    @Override
    public Faculty findById(int id) {
        return facultyRepo.findById(id).get();
    }

    @Override
    public Faculty save(Faculty faculty) {
        return facultyRepo.save(faculty);
    }
}