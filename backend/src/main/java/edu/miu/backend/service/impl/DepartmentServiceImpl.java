package edu.miu.backend.service.impl;

import edu.miu.backend.dto.DepartmentDto;
import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.Department;
import edu.miu.backend.repo.DepartmentRepo;
import edu.miu.backend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepo departmentRepo;


    @Override
    public List<Department> findAll() {
        return (List<Department>) departmentRepo.findAll();
    }

    @Override
    public Department findById(int id) {
        return departmentRepo.findById(id).get();
    }

    @Override
    public Department save(Department department) {
        return departmentRepo.save(department);
    }

    @Override
    public void delete(int id) {
        departmentRepo.deleteById(id);
    }

    @Override
    public DepartmentDto update(DepartmentDto departmentDto, int id) throws Exception {
        Department department = departmentRepo.findById(id).orElseGet(null);
        if(department == null){
            throw new Exception("Department not found");
        }

        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());

        return departmentDto;
    }


}
