package edu.miu.backend.controller;

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
        return ResponseEntity.ok().body(studentService.findByID(id));
    }

    @PostMapping()
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return ResponseEntity.ok().body(studentService.save(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable int id){
        return null; //ResponseEntity.ok().body(studentService.update(student, id));
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id){
        studentService.delete(id);

    }

}
