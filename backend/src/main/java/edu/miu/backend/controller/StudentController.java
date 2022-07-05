package edu.miu.backend.controller;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.entity.Student;
import edu.miu.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/student")
public class StudentController {
    private final StudentService studentService;


    @GetMapping("")
    public ResponseEntity<List<Student>> getAll(){
        return ResponseEntity.ok().body(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id){
        return ResponseEntity.ok().body(studentService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto){
        return ResponseEntity.ok().body(studentService.save(studentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto, @PathVariable int id) throws Exception {
        return  ResponseEntity.ok().body(studentService.update(studentDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id){
        studentService.delete(id);
    }

}
