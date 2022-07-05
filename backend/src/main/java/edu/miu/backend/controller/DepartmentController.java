package edu.miu.backend.controller;

import edu.miu.backend.dto.DepartmentDto;
import edu.miu.backend.entity.Department;
import edu.miu.backend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/department")
public class DepartmentController {
    private final DepartmentService departmentService;


    @GetMapping("")
    public ResponseEntity<List<Department>> getAll(){
        return ResponseEntity.ok().body(departmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable int id){
        return ResponseEntity.ok().body(departmentService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Department> addDepartment(@RequestBody Department department){
        return ResponseEntity.ok().body(departmentService.save(department));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto departmentDto, @PathVariable int id) throws Exception {
        return  ResponseEntity.ok().body(departmentService.update(departmentDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable int id){
        departmentService.delete(id);
    }

}
