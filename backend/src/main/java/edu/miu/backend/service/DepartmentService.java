package edu.miu.backend.service;

import edu.miu.backend.dto.DepartmentDto;
import edu.miu.backend.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();

    Department findById(int id);


    Department save(Department student);

    void delete(int id);

    DepartmentDto update(DepartmentDto departmentDto, int id) throws Exception;
}
