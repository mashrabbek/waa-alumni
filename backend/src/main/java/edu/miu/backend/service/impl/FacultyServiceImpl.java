package edu.miu.backend.service.impl;

import edu.miu.backend.dto.FacultyDto;
import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.Faculty;
import edu.miu.backend.repo.DepartmentRepo;
import edu.miu.backend.repo.FacultyRepo;
import edu.miu.backend.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepo facultyRepo;

    private final DepartmentRepo departmentRepo;

    private final ModelMapper modelMapper;

    @Override
    public List<Faculty> findAll() {
        return (List<Faculty>) facultyRepo.findAll();
    }

    @Override
    public Faculty findById(int id) {
        return facultyRepo.findById(id).get();
    }

    @Override
    public FacultyDto save(FacultyDto facultyDto) throws Exception {
        Faculty faculty = modelMapper.map(facultyDto, Faculty.class);
        Department department = departmentRepo.findById(facultyDto.getDepartmentId())
                .orElseThrow(()-> new Exception("Faculty not found"));
        faculty.setDepartment(department);
        return modelMapper.map(facultyRepo.save(faculty), FacultyDto.class);
    }

    @Override
    @Transactional
    public FacultyDto update(FacultyDto facultyDto, int id) throws Exception {

        Faculty stMapped = modelMapper.map(facultyDto, Faculty.class);
        Department depRef = departmentRepo.findById(facultyDto.getDepartmentId())
                .orElseThrow(()->new Exception("Department not found!"));

        Faculty faculty = facultyRepo.findById(id)
                .orElseThrow(()-> new Exception("Faculty not found!"));
        faculty.setEmail(stMapped.getEmail());
        faculty.setFirstName(stMapped.getFirstName());
        faculty.setLastName(stMapped.getLastName());
        faculty.setDepartment(depRef);
        faculty.setAddress(stMapped.getAddress());

        return facultyDto;
    }

    @Override
    public void delete(int id) {
        facultyRepo.deleteById(id);
    }
}
