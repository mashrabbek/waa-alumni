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
public class FacultyController {
    private final FacultyService facultyService;


    @GetMapping("")
    public ResponseEntity<List<Faculty>> getAll(){
        return ResponseEntity.ok().body(facultyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getById(@PathVariable int id){
        return ResponseEntity.ok().body(facultyService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty){
        return ResponseEntity.ok().body(facultyService.save(faculty));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacultyDto> updateFaculty(@RequestBody FacultyDto facultyDto, @PathVariable int id) throws Exception {
        return  ResponseEntity.ok().body(facultyService.update(facultyDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable int id){
        facultyService.delete(id);
    }

}
