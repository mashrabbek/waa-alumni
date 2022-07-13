package edu.miu.backend.service.impl;

import edu.miu.backend.dto.FacultyDto;
import edu.miu.backend.entity.Address;
import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.Faculty;
import edu.miu.backend.repo.DepartmentRepo;
import edu.miu.backend.repo.FacultyRepo;
import edu.miu.backend.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepo facultyRepo;

    private final DepartmentRepo departmentRepo;

    private final ModelMapper modelMapper;

    @Override
    public List<FacultyDto> findAll() {
        List<Faculty> faculties = (List<Faculty>) facultyRepo.findAll();
        return faculties.stream().map(faculty -> modelMapper.map(faculties, FacultyDto.class)).collect(Collectors.toList());
    }

    @Override
    public Faculty findById(int id) {
        return facultyRepo.findById(id).orElse(null);
    }

    @Override
    public FacultyDto save(FacultyDto facultyDto) throws Exception {
        Optional<Faculty> facultyRef = facultyRepo.findByUsername(facultyDto.getUsername());
        if (facultyRef.isPresent()){
            Optional<Department> dep = departmentRepo.findById(facultyDto.getDepartmentId());
            if (dep.isPresent()) {
                facultyRef.get().setDepartment(dep.get());
            }
            if (facultyDto.getAddress() != null) {
                facultyRef.get().setAddress(modelMapper.map(facultyDto.getAddress(), Address.class));
            }
            return facultyDto;
        }
        Faculty faculty = modelMapper.map(facultyDto, Faculty.class);
        Department department = departmentRepo.findById(facultyDto.getDepartmentId())
                .orElseThrow(()-> new Exception("Faculty not found"));
        faculty.setDepartment(department);
        return modelMapper.map(facultyRepo.save(faculty), FacultyDto.class);
    }

    @Override
    public FacultyDto update(FacultyDto facultyDto, String username) throws Exception {

        Faculty facultyMapped = modelMapper.map(facultyDto, Faculty.class);
        Department department = departmentRepo.findById(facultyDto.getDepartmentId())
                .orElseThrow(()->new Exception("Department not found!"));

        Faculty faculty = facultyRepo.findByUsername(username)
                .orElseThrow(()-> new Exception("Faculty not found!"));

        faculty.setDepartment(department);
        faculty.setAddress(facultyMapped.getAddress());

        return facultyDto;
    }

    @Override
    public void delete(int id) {
        facultyRepo.deleteById(id);
    }

    @Override
    public FacultyDto findByUsername(String username) {
        Optional<Faculty> faculty = facultyRepo.findByUsername(username);
        if (!faculty.isPresent()){return null;}
        FacultyDto facultyDto = modelMapper.map(faculty, FacultyDto.class);
        facultyDto.setDepartmentId(faculty.get().getDepartment().getId());
        return facultyDto;
    }

    @Override
    public void deleteByUsername(String username) {
        facultyRepo.deleteByUsername(username);
    }
}
