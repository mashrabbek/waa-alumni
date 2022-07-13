package edu.miu.backend.controller;

import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.dto.JobApplicationDto;
import edu.miu.backend.dto.responseDto.JobAdResponseDto;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.service.JobAdService;
import edu.miu.backend.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobApplication")
@CrossOrigin(origins = "*")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

//    @GetMapping("")
//    public ResponseEntity<List<JobAd>> getAll(){
//        return ResponseEntity.ok().body(jobApplicationService.findAll());
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<JobAdDto> getById(@PathVariable int id){
//        return ResponseEntity.ok().body(jobAdService.findById(id));
//    }

    @GetMapping("/{username}")
    public ResponseEntity<List<JobApplicationDto>> getById(@PathVariable String username){
        return ResponseEntity.ok().body(jobApplicationService.findByUsername(username));
    }

    @PostMapping(path = "")
    public ResponseEntity<JobApplicationDto> addJobAd(@RequestBody JobApplicationDto jobAdDto) throws IOException {
        return ResponseEntity.ok().body(jobApplicationService.save(jobAdDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationDto> updateJobAd(@RequestBody JobApplicationDto jobAdDto, @PathVariable int id) throws Exception {
        return  ResponseEntity.ok().body(jobApplicationService.update(jobAdDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteJobAd(@PathVariable int id){
        jobApplicationService.delete(id);
    }
}
