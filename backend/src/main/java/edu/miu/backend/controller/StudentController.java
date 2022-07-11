package edu.miu.backend.controller;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.dto.StudentResponseDto;
import edu.miu.backend.entity.Student;
import edu.miu.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/student")
@CrossOrigin(origins = "*")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("")
    public ResponseEntity<List<StudentResponseDto>> getAll(){
        return ResponseEntity.ok().body(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id){
        return ResponseEntity.ok().body(studentService.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Student> getByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(studentService.findByUsername(username));
    }

    @PostMapping()
    public ResponseEntity<StudentResponseDto> addStudent(@ModelAttribute StudentDto studentDto) throws SQLException {
        return ResponseEntity.ok().body(studentService.save(studentDto));
    }

    @PutMapping("/{username}")
    public ResponseEntity<StudentResponseDto> updateStudent(@ModelAttribute StudentDto studentDto, @PathVariable String username) throws Exception {
        return ResponseEntity.ok().body(studentService.update(studentDto, username));
    }

//    @DeleteMapping("/{id}")
//    public void deleteStudent(@PathVariable int id){
//        studentService.delete(id);
//    }

    @DeleteMapping("/{username}")
    public void deleteStudentByUsername(@PathVariable String username){
        studentService.deleteByUsername(username);
    }
}
