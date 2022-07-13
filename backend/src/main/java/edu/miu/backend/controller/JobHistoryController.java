package edu.miu.backend.controller;

import edu.miu.backend.dto.JobHistoryDto;
import edu.miu.backend.dto.responseDto.JobHistoryResponseDto;
import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.service.JobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "*")
public class JobHistoryController {

    @Autowired
    private JobHistoryService jobHistoryService;

    @GetMapping("")
    public ResponseEntity<List<JobHistoryResponseDto>> getAll(){
        return ResponseEntity.ok().body(jobHistoryService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<JobHistoryResponseDto>> getById(@PathVariable String username){
        return ResponseEntity.ok().body(jobHistoryService.findByUsername(username));
    }
    @PostMapping("/{username}")
    public ResponseEntity<JobHistoryResponseDto> addJobHistory(@RequestBody JobHistoryDto jobHistoryDto, @PathVariable String username){
        System.out.println();
        return ResponseEntity.ok().body(jobHistoryService.save(jobHistoryDto, username));
    }
    @DeleteMapping("/{id}")
    public void deleteJobHistory(@PathVariable int id){
        jobHistoryService.deleteByID(id);
    }
}
