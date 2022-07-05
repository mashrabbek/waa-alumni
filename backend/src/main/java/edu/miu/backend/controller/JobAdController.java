package edu.miu.backend.controller;

import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/jobAd")
public class JobAdController {
    private final JobAdService jobAdService;


    @GetMapping("")
    public ResponseEntity<List<JobAd>> getAll(){
        return ResponseEntity.ok().body(jobAdService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobAd> getById(@PathVariable int id){
        return ResponseEntity.ok().body(jobAdService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<JobAd> addJobAd(@RequestBody JobAd jobAd){
        return ResponseEntity.ok().body(jobAdService.save(jobAd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobAdDto> updateJobAd(@RequestBody JobAdDto jobAdDto, @PathVariable int id) throws Exception {
        return  ResponseEntity.ok().body(jobAdService.update(jobAdDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteJobAd(@PathVariable int id){
        jobAdService.delete(id);
    }

}
