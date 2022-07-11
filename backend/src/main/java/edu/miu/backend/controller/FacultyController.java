package edu.miu.backend.controller;

import edu.miu.backend.dto.FacultyDto;
import edu.miu.backend.entity.Faculty;
import edu.miu.backend.repo.FacultyRepo;
import edu.miu.backend.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/faculty")
@CrossOrigin(origins = "*")
public class FacultyController {
    private final FacultyService facultyService;


    @GetMapping("")
    public ResponseEntity<List<FacultyDto>> getAll(){
        return ResponseEntity.ok().body(facultyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getById(@PathVariable int id){
        return ResponseEntity.ok().body(facultyService.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<FacultyDto> getByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(facultyService.findByUsername(username));
    }

    @PostMapping("")
    public ResponseEntity<FacultyDto> addFaculty(@RequestBody FacultyDto facultyDto) throws Exception {
        return ResponseEntity.ok().body(facultyService.save(facultyDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacultyDto> updateFaculty(@RequestBody FacultyDto facultyDto, @PathVariable String username) throws Exception {
        return  ResponseEntity.ok().body(facultyService.update(facultyDto, username));
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable int id){
        facultyService.delete(id);
    }

}
