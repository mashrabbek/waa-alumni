package edu.miu.backend.controller;
import edu.miu.backend.dto.StudentCommentDto;
import edu.miu.backend.service.StudentCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studentcomment")
public class StudentCommentController {
    
    private final StudentCommentService studentCommentService;

    @GetMapping("")
    public ResponseEntity<List<StudentCommentDto>> getAll(){
        return ResponseEntity.ok().body(studentCommentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCommentDto> getById(@PathVariable int id){
        return ResponseEntity.ok().body(studentCommentService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<StudentCommentDto> addStudentComment(@RequestBody StudentCommentDto studentCommentDto){
        return ResponseEntity.ok().body(studentCommentService.save(studentCommentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCommentDto> updateStudentComment(@RequestBody StudentCommentDto studentCommentDto, @PathVariable int id) throws Exception {
        return  ResponseEntity.ok().body(studentCommentService.update(studentCommentDto, id));
    }

    @DeleteMapping("/{id}")
    public String deleteStudentComment(@PathVariable int id){
        studentCommentService.delete(id);
        return "Deleted";
    }
}
