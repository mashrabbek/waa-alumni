package edu.miu.backend.controller;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.dto.responseDto.StudentResponseDto;
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

    @GetMapping("/{username}")
    public ResponseEntity<StudentResponseDto> getByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(studentService.findByUsername(username));
    }

    @PostMapping("")
    public ResponseEntity<StudentResponseDto> addStudent(@ModelAttribute StudentDto studentDto) throws SQLException {
        return ResponseEntity.ok().body(studentService.save(studentDto));
    }

    @PutMapping("/{username}")
    public ResponseEntity<StudentResponseDto> updateStudent(@ModelAttribute StudentDto studentDto, @PathVariable String username) throws Exception {
        return ResponseEntity.ok().body(studentService.update(studentDto, username));
    }

    @DeleteMapping("/{username}")
    public void deleteStudentByUsername(@PathVariable String username){
        studentService.deleteByUsername(username);
    }
}
